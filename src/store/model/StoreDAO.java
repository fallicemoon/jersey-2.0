package store.model;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import tools.AbstractDAO;
import tools.JerseyEnum.StoreType;

public class StoreDAO extends AbstractDAO<StoreVO> {
	

	public StoreDAO() {
		super(StoreVO.class, "storeId");
		
	}

//	public List<StoreVO> getAll() {
//		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
//		session.getTransaction().begin();
//		List<StoreVO> list = session.createQuery("from StoreVo").list();
//		session.getTransaction().commit();
//		return list;
//	}
//
//	public StoreVO getOne(Integer id) {
//		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
//		session.getTransaction().begin();
//		StoreVO vo = (StoreVO) session.get(StoreVO.class, id);
//		session.getTransaction().commit();
//		return vo;
//	}
//
//	public Integer create(StoreVO vo) {
//		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
//		try {
//			session.beginTransaction();
//			Integer id = (Integer) session.save(vo);
//			session.getTransaction().commit();
//			
//			return id;
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			return null;
//		}		
//	}
//
//	public boolean update(StoreVO vo) {
//		String sql = "UPDATE store SET type=?, name=? where store_id=?";
//		PreparedStatement preparedStatement = getPreparedStatement(sql);
//		try {
//			preparedStatement.setString(1, vo.getType());
//			preparedStatement.setString(2, vo.getName());
//
//			preparedStatement.setInt(3, vo.getStoreId().intValue());
//
//			int count = preparedStatement.executeUpdate();
//			connection.close();
//			if (count == 1)
//				return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public boolean delete(Integer id) {
//		String sql = "DELETE FROM store where store_id = ?";
//		PreparedStatement preparedStatement = getPreparedStatement(sql);
//		try {
//			preparedStatement.setInt(1, id.intValue());
//
//			int count = preparedStatement.executeUpdate();
//			connection.close();
//			if (count == 1)
//				return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	public List<StoreVO> getStoreListByType(StoreType type) {
		
		return getHelper(Restrictions.eq("type", type));
		
//		String sql = "SELECT * FROM store WHERE type=?";
//		PreparedStatement preparedStatement = getPreparedStatement(sql);
//		List<StoreVO> list = new ArrayList<StoreVO>();
//		try {
//			preparedStatement.setString(1, type);
//			ResultSet rs = preparedStatement.executeQuery();
//			while (rs.next()) {
//				StoreVO storeVO = new StoreVO();
//				storeVO.setStoreId(Integer.valueOf(rs.getInt(1)));
//				storeVO.setType(rs.getString(2));
//				storeVO.setName(rs.getString(3));
//				list.add(storeVO);
//			}
//			connection.close();
//			return list;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
	}

//	public boolean delete(Integer[] ids) {
//		String sql = "DELETE FROM store WHERE store_id IN (";
//		int length = ids.length;
//		for (int i = 0; i < ids.length; i++) {
//			sql = sql.concat("?,");
//		}
//		sql = sql.substring(0, sql.length() - 1);
//		sql = sql.concat(")");
//		PreparedStatement preparedStatement = getPreparedStatement(sql);
//		try {
//			for (int i = 0; i < ids.length; i++) {
//				preparedStatement.setInt(i + 1, ids[i].intValue());
//			}
//			int count = preparedStatement.executeUpdate();
//			connection.close();
//			if (count == length)
//				return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
	// }
}
