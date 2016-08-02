package com.jersey.picture.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateSessionFactory;

@Repository
public class PictureDAO extends AbstractDAO<PictureVO> {
	// private DataSource ds;
	// private Connection connection;
	// private PreparedStatement preparedStatement;
	// private static final String GETALL = "SELECT * FROM picture";
	// private static final String GETONE = "SELECT * FROM picture WHERE
	// picture_id=?";
	// private static final String GET_BY_COMMODITY_ID = "SELECT * FROM picture
	// WHERE commodity_id=? ORDER BY sequence_id";
	// private static final String GET_MAX_SEQUENCE_ID = "SELECT
	// MAX(sequence_id) FROM picture where commodity_id=?";
	// private static final String CREATE = "INSERT INTO picture (commodity_id,
	// sequence_id, picture, file_name) VALUES (?,?,?,?)";
	// private static final String UPDATE = "UPDATE picture SET commodity_id=?,
	// picture=? WHERE picture_id=?";
	// private static final String DELETE = "DELETE FROM picture where
	// picture_id=?";
	// private static final String GET_PICTURE_COUNTS_BY_COMMODITY_IDS = "select
	// commodity_id, count(*) as count from picture group by commodity_id";
	// private static final String GET_PICTURE_COUNTS_BY_COMMODITY_ID = "select
	// count(*) as count from picture where commodity_id=?";

	private final static String COMMODITY_ID_PICTURE_COUNT_MAP = "select commodityVO as commodityVO, count(*) as count from PictureVO picture group by picture.commodityVO";
	private final static String GET_COUNT_BY_COMMODITY_ID = "select count(*) as count from PictureVO picture where picture.commodityVO = :commodityVO";
	private final static String GET_NEXT_SEQUENCE_ID = "select MAX(sequenceId)+1 from PictureVO where commodityVO = :commodityVO";
	// static {
	// try {
	// Class.forName("org.mariadb.jdbc.Driver");
	//
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public PictureDAO() {
		super(PictureVO.class, "pictureId");
		// try {
		// ds = (DataSource)new
		// InitialContext().lookup("java:comp/env/jdbc/jersey");
		// } catch (NamingException e) {

		// e.printStackTrace();
		// }
	}

	// public List<PictureVO> getAll() {
	// List<PictureVO> list = new ArrayList<PictureVO>();
	// try {
	// connection = ds.getConnection();
	// this.preparedStatement = this.connection.prepareStatement("SELECT * FROM
	// picture");
	// ResultSet rs = this.preparedStatement.executeQuery();
	// while (rs.next()) {
	// PictureVO vo = new PictureVO();
	// vo.setPictureId(Integer.valueOf(rs.getInt("picture_id")));
	// vo.setCommodityId(Integer.valueOf(rs.getInt("commodity_id")));
	// vo.setSequenceId(Integer.valueOf(rs.getInt("sequence_id")));
	// vo.setPicture(rs.getBinaryStream("picture"));
	// vo.setFileName(rs.getString("file_name"));
	// list.add(vo);
	// }
	// connection.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return list;
	// }

	public List<PictureVO> getPictureIds(Integer commodityId) {
		String[] columnNames = new String[] { "pictureId" };
		CommodityVO commodityVO = new CommodityVO();
		commodityVO.setCommodityId(commodityId);

		return getHelper(columnNames, Restrictions.eq("commodityVO", commodityVO));
	}

	// public PictureVO getOne(Integer pictureId) {
	// PictureVO vo = new PictureVO();
	// try {
	// connection = ds.getConnection();
	// this.preparedStatement = this.connection.prepareStatement("SELECT * FROM
	// picture WHERE picture_id=?");
	// this.preparedStatement.setInt(1, pictureId.intValue());
	// ResultSet rs = this.preparedStatement.executeQuery();
	// if (!rs.next()) {
	// connection.close();
	// return vo;
	// }
	// vo.setPictureId(Integer.valueOf(rs.getInt("picture_id")));
	// vo.setCommodityId(Integer.valueOf(rs.getInt("commodity_id")));
	// vo.setSequenceId(Integer.valueOf(rs.getInt("sequence_id")));
	// vo.setPicture(rs.getBinaryStream("picture"));
	// vo.setFileName(rs.getString("file_name"));
	// connection.close();
	// return vo;
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return vo;
	// }

	public List<PictureVO> getPicturesByCommodityId(Integer commodityId) {
		CommodityVO commodityVO = new CommodityVO();
		commodityVO.setCommodityId(commodityId);
		return getHelper(null, Order.asc("sequenceId"), Restrictions.eq("commodityVO", commodityVO));
		// try {
		// connection = ds.getConnection();
		// this.preparedStatement = this.connection
		// .prepareStatement("SELECT * FROM picture WHERE commodity_id=? ORDER
		// BY sequence_id");
		// this.preparedStatement.setInt(1, commodityId.intValue());
		// ResultSet rs = this.preparedStatement.executeQuery();
		//
		// while (rs.next()) {
		// PictureVO pictureVO = new PictureVO();
		// pictureVO.setPictureId(Integer.valueOf(rs.getInt("picture_id")));
		// pictureVO.setCommodityId(Integer.valueOf(rs.getInt("commodity_id")));
		// pictureVO.setSequenceId(Integer.valueOf(rs.getInt("sequence_id")));
		// pictureVO.setPicture(rs.getBinaryStream("picture"));
		// pictureVO.setFileName(rs.getString("file_name"));
		// list.add(pictureVO);
		// }
		// connection.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// return list;
	}

	public Map<Integer, Integer> getCommodityIdPictureCountMap() {
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		List<Map<String, Object>> result;
		try {
			Query query = session.createQuery(COMMODITY_ID_PICTURE_COUNT_MAP);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			result = new ArrayList<>();
			e.printStackTrace();
		}

		Map<Integer, Integer> resultMap = new HashMap<>();
		for (Map<String, Object> map : result) {
			CommodityVO commodityVO = (CommodityVO) map.get("commodityVO");
			Long count = (Long) map.get("count");
			resultMap.put(commodityVO.getCommodityId(), count.intValue());
		}
		return resultMap;
	}

	public Integer getCommodityIdPictureCount(Integer commodityId) {
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();

		Integer count;
		try {
			Map<String, Long> map;
			Query query = session.createQuery(GET_COUNT_BY_COMMODITY_ID);
			CommodityVO commodityVO = new CommodityVO();
			commodityVO.setCommodityId(commodityId);
			query.setEntity("commodityVO", commodityVO);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			map = (Map<String, Long>) query.uniqueResult();
			session.getTransaction().commit();
			count = map.get("count").intValue();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			count = 0;
		}
		return count;
	}
	
	@Override
	public PictureVO create (PictureVO pictureVO) {
		pictureVO.setSequenceId(getSequenceId(pictureVO.getCommodityVO()));
		return super.create(pictureVO);
	}
	
	private Integer getSequenceId (CommodityVO commodityVO) {
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		try {
			Integer sequenceId = (Integer)session.createQuery(GET_NEXT_SEQUENCE_ID).setParameter("commodityVO", commodityVO).uniqueResult();
			session.getTransaction().commit();
			if (sequenceId==null) {
				sequenceId = 1;
			}
			return sequenceId;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}
	
	// public Integer create(PictureVO vo) {
	// try {
	// connection = ds.getConnection();
	// this.preparedStatement = this.connection
	// .prepareStatement("SELECT MAX(sequence_id) FROM picture where
	// commodity_id=?");
	// this.preparedStatement.setInt(1, vo.getCommodityId().intValue());
	// ResultSet rs = this.preparedStatement.executeQuery();
	// Integer sequenceId;
	// if (rs.next())
	// sequenceId = Integer.valueOf(rs.getInt(1) + 1);
	// else {
	// sequenceId = Integer.valueOf(1);
	// }
	// this.preparedStatement = this.connection.prepareStatement(
	// "INSERT INTO picture (commodity_id, sequence_id, picture, file_name)
	// VALUES (?,?,?,?)");
	// this.preparedStatement.setInt(1, vo.getCommodityId().intValue());
	// this.preparedStatement.setInt(2, sequenceId.intValue());
	// this.preparedStatement.setBinaryStream(3, vo.getPicture());
	// this.preparedStatement.setString(4, vo.getFileName());
	// this.preparedStatement.executeUpdate();
	// connection.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return Integer.valueOf(1);
	// }
	//
	// public boolean update(PictureVO vo) {
	// boolean check = true;
	// try {
	// connection = ds.getConnection();
	// this.preparedStatement = this.connection
	// .prepareStatement("UPDATE picture SET commodity_id=?, picture=? WHERE
	// picture_id=?");
	// this.preparedStatement.setInt(1, vo.getCommodityId().intValue());
	// this.preparedStatement.setBinaryStream(2, vo.getPicture());
	// this.preparedStatement.setInt(3, vo.getPictureId().intValue());
	// this.preparedStatement.setString(4, vo.getFileName());
	// this.preparedStatement.executeUpdate();
	// connection.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// check = false;
	// }
	// return check;
	// }
	//
	// public boolean delete(Integer id) {
	// boolean check = true;
	// try {
	// connection = ds.getConnection();
	// this.preparedStatement = this.connection.prepareStatement("DELETE FROM
	// picture where picture_id=?");
	// this.preparedStatement.setInt(1, id.intValue());
	// this.preparedStatement.executeUpdate();
	// connection.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// check = false;
	// }
	// return check;
	// }
	//
	// public boolean delete(Integer[] ids) {
	// for (Integer id : ids) {
	// delete(id);
	// }
	// return true;
	// }

}
