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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;

@Repository
public class PictureDAO extends AbstractDAO<PictureVO> {
	
	@Autowired
	private HibernateTools hibernateTools;

	private final static String COMMODITY_ID_PICTURE_COUNT_MAP = "select commodityVO as commodityVO, count(*) as count from PictureVO picture group by picture.commodityVO";
	private final static String GET_COUNT_BY_COMMODITY_ID = "select count(*) as count from PictureVO picture where picture.commodityVO = :commodityVO";
	private final static String GET_NEXT_SEQUENCE_ID = "select MAX(sequenceId)+1 from PictureVO where commodityVO = :commodityVO";

	public PictureDAO() {
		super(PictureVO.class);
	}

	public List<PictureVO> getPictureIds(String commodityId) {
		String[] columnNames = new String[] { "pictureId" };
		CommodityVO commodityVO = new CommodityVO();
		commodityVO.setId(commodityId);

		return getHelper(columnNames, Restrictions.eq("commodityVO", commodityVO));
	}

	public List<PictureVO> getPicturesByCommodityId(String commodityId) {
		CommodityVO commodityVO = new CommodityVO();
		commodityVO.setId(commodityId);
		return getHelper(null, Order.asc("sequenceId"), Restrictions.eq("commodityVO", commodityVO));
	}

	public Map<String, Integer> getCommodityIdPictureCountMap() {
		Session session = hibernateTools.getSession();
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

		Map<String, Integer> resultMap = new HashMap<>();
		for (Map<String, Object> map : result) {
			CommodityVO commodityVO = (CommodityVO) map.get("commodityVO");
			Long count = (Long) map.get("count");
			resultMap.put(commodityVO.getId(), count.intValue());
		}
		return resultMap;
	}

	public Integer getCommodityIdPictureCount(String commodityId) {
		Session session = hibernateTools.getSession();
		session.beginTransaction();

		Integer count;
		try {
			Map<String, Long> map;
			Query query = session.createQuery(GET_COUNT_BY_COMMODITY_ID);
			CommodityVO commodityVO = new CommodityVO();
			commodityVO.setId(commodityId);
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
		Session session = hibernateTools.getSession();
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

}
