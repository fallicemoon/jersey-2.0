package com.jersey.commodity.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;

@Repository
public class CommodityDAO extends AbstractDAO<CommodityVO> {

	public CommodityDAO() {
		super(CommodityVO.class, "commodityId");
	}
	
	public List<Object> getCommodityAttrValue (Integer commodityId) {
		String hql = "select ca.commodityAttr, cam.commodityAttrValue, ca.commodityAttrAuthority from CommodityVO c "
				+ "left join c.commodityId as cam left outer join cam.commodityAttrVO as ca "
				+ "where c.commodityVO=:commodityVO";
		List<Object> list;
		Session session = HibernateTools.getSession();
		session.beginTransaction();
		try {
			CommodityVO commodityVO = new CommodityVO();
			commodityVO.setCommodityId(commodityId);
			Query query = session.createQuery(hql).setParameter("commodityVO", commodityVO);
			list = query.list();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			list = new ArrayList<>();
		}
		return list;
	}

	// public List<CommodityVO> getAll() {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// List<CommodityVO> list = session.createQuery("from CommodityVO").list();
	// session.getTransaction().commit();
	//
	// return list;
	// }
	//
	// public CommodityVO getOne(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// CommodityVO vo = (CommodityVO) session.get(CommodityVO.class, id);
	// session.getTransaction().commit();
	//
	// return vo;
	// }

//	public List<CommodityVO> getByRule(Map<String, Object> rule) {
//		Criterion[] criterions = new Criterion[rule.size()];
//		int i = 0;
//		for (String key : rule.keySet()) {
//			if (i < rule.size()) {
//				Object value = rule.get(key);
//				criterions[i] = Restrictions.eq(key, value);
//				i++;
//			}
//		}
//		return getHelper(criterions);
//	}

	// public Integer create(CommodityVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// Integer id = (Integer) session.save(vo);
	// session.getTransaction().commit();
	//
	// return id;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// public boolean update(CommodityVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// session.update(vo);
	// session.getTransaction().commit();
	//
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	// public boolean delete(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// CommodityVO vo = new CommodityVO();
	// vo.setCommodityId(id);
	// session.delete(vo);
	// session.getTransaction().commit();
	//
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	// public boolean delete(Integer[] ids) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.beginTransaction();
	//
	// for (Integer id : ids) {
	// try {
	// CommodityVO vo = new CommodityVO();
	// vo.setCommodityId(id);
	// session.delete(vo);
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	//
	// session.getTransaction().commit();
	//
	// return true;
	// }

	public List<CommodityVO> getByPurchaseCaseIdIsNull() {

		return getHelper(Restrictions.isNull("purchaseCaseVO"));
		// Session session = HibernateSessionFactory.getSession();
		// session.getTransaction().begin();
		// List<CommodityVO> list = session.createQuery("from CommodityVO where
		// purchaseCaseVO is null").list();
		// session.getTransaction().commit();
		//
		// return list;
	}

	public void updatePurchaseCaseId(PurchaseCaseVO purchaseCaseVO, Integer[] commodityIds) {
		Session session = HibernateTools.getSession();
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
		Session session = HibernateTools.getSession();
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
		Session session = HibernateTools.getSession();
		try {
			session.beginTransaction();
			session.createQuery("delete from PictureVO vo where vo.commodityVO.commodityId in (:ids)").setParameterList("ids", ids).executeUpdate();
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