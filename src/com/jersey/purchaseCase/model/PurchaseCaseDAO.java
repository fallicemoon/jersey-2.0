package com.jersey.purchaseCase.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.sellCase.model.SellCaseVO;
import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;

@Repository
public class PurchaseCaseDAO extends AbstractDAO<PurchaseCaseVO> {

	@Autowired
	private HibernateTools hibernateTools;
	
	public PurchaseCaseDAO() {
		super(PurchaseCaseVO.class);
	}

	public List<PurchaseCaseVO> getAllOfNotProgress(String progress) {
		return getHelper(Restrictions.ne("progress", progress));
	}

	public List<PurchaseCaseVO> getPurchaseCasesBySellCaseIdIsNull() {
		return getHelper(Restrictions.isNull("sellCaseVO"));
	}

	public boolean updateSellCaseId(String sellCaseId, Integer[] purchaseCaseIds) {
		Session session = hibernateTools.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			SellCaseVO sellCaseVO = new SellCaseVO();
			sellCaseVO.setId(sellCaseId);

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class, purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(sellCaseVO);
				session.update(purchaseCaseVO);
			}

			// 避免batch太大, out of memory
			int count = 0;
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteSellCaseId(Integer[] purchaseCaseIds) {
		Session session = hibernateTools.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class, purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(null);
				session.update(purchaseCaseVO);
			}

			// 避免batch太大, out of memory
			int count = 0;
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
	//先刪掉商品再刪進貨
	@Override
	public boolean delete(Integer[] ids) {
		Session session = hibernateTools.getSession();
		try {
			session.beginTransaction();
			session.createQuery("delete from CommodityVO vo where vo.purchaseCaseVO.purchaseCaseId in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.createQuery("delete from PurchaseCaseVO vo where vo.id in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
}
