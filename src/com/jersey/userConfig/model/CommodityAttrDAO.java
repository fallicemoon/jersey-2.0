package com.jersey.userConfig.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;

@Repository
public class CommodityAttrDAO extends AbstractDAO<CommodityAttrVO> {

	public CommodityAttrDAO(Class<CommodityAttrVO> type, String pk) {
		super(CommodityAttrVO.class, "commodityAttrId");
	}
	
	public List<String> getCommodityTypeList () {
		String hql = "select distinct c.commodityType from CommodityAttr c";
		Session session = HibernateTools.getSession();
		session.beginTransaction();
		try {
			List<String> commodityTypes = session.createQuery(hql).list();
			session.getTransaction().commit();
			return commodityTypes;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw e;
		}
	}
	
	public List<CommodityAttrVO> getCommodityAttrList (String commodityType) {
		return getHelper(Restrictions.eq("commodityType", commodityType));
	}

}
