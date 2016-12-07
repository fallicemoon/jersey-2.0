package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.userConfig.model.CommodityTypeVO;

@Repository
public class CommodityDAO extends AbstractDAO<CommodityVO> {

	@Autowired
	private HibernateTools hibernateTools;
	
	public CommodityDAO() {
		super(CommodityVO.class, "commodityId");
	}
	
	public List<CommodityVO> getAll (Authority authority, CommodityTypeVO commodityTypeVO) {
		Session session = hibernateTools.getSession();
		session.getTransaction().begin();
		List<CommodityVO> list;
		try {
			Criteria criteria = session.createCriteria(CommodityVO.class);
			if (authority==Authority.CUSTOMER) {
				criteria.add(Restrictions.eq("authority", authority));
			}
			criteria.add(Restrictions.eq("commodityTypeVO", commodityTypeVO));		
			list = criteria.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			list = new ArrayList<>();
		}
		return list;
	}
	
	public List<CommodityVO> getAll (Authority authority, CommodityTypeVO commodityTypeVO, Integer pageSize, Integer page) {
		Session session = hibernateTools.getSession();
		session.getTransaction().begin();
		List<CommodityVO> list;
		try {
			Criteria criteria = session.createCriteria(CommodityVO.class);
			if (authority==Authority.CUSTOMER) {
				criteria.add(Restrictions.eq("authority", authority));
			}
			criteria.add(Restrictions.eq("commodityTypeVO", commodityTypeVO));
			//分頁
			Integer firstResult = pageSize*(page-1);
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(pageSize);
			
			list = criteria.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			list = new ArrayList<>();
		}
		return list;
	}
	
	public Long getTotalCount (Authority authority, CommodityTypeVO commodityTypeVO) {
		return super.getTotalCount(Restrictions.eq("authority", authority), Restrictions.eq("commodityTypeVO", commodityTypeVO));
	}
	
	public List<CommodityVO> getByPurchaseCaseIdIsNull() {
		return getHelper(Restrictions.isNull("purchaseCaseVO"));
	}

	public void updatePurchaseCaseId(PurchaseCaseVO purchaseCaseVO, Integer[] commodityIds) {
		Session session = hibernateTools.getSession();
		session.getTransaction().begin();
		try {
			for (Integer commodityId : commodityIds) {
				CommodityVO commodityVO = (CommodityVO) session.load(CommodityVO.class, commodityId);
				commodityVO.setPurchaseCaseVO(purchaseCaseVO);
				session.update(commodityVO);

				// 避免batch太大, out of memory
				int count = 0;
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public void deletePurchaseCaseId(Integer[] commodityIds) {
		Session session = hibernateTools.getSession();
		session.getTransaction().begin();
		try {
			for (Integer commodityId : commodityIds) {
				CommodityVO commodityVO = (CommodityVO) session.load(CommodityVO.class, commodityId);
				commodityVO.setPurchaseCaseVO(null);
				session.update(commodityVO);

				// 避免batch太大, out of memory
				int count = 0;
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	//先刪掉圖片再刪商品
	@Override
	public boolean delete(Integer[] ids) {
		Session session = hibernateTools.getSession();
		try {
			session.beginTransaction();
			session.createQuery("delete from PictureVO vo where vo.commodityVO.commodityId in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.createQuery("delete from CommodityAttrMappingVO vo where vo.commodityVO.commodityId in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.createQuery("delete from CommodityVO vo where vo.commodityId in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

}