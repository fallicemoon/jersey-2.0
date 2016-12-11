package com.jersey.userConfig.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.Authority;

@Repository
public class CommodityTypeDAO extends AbstractDAO<CommodityTypeVO> {

	@Autowired
	private HibernateTools hibernateTools;
	
	public CommodityTypeDAO() {
		super(CommodityTypeVO.class);
	}
	
	public List<CommodityTypeVO> getAll (Authority authority) {
		Session session = hibernateTools.getSession();
		session.getTransaction().begin();
		List<CommodityTypeVO> list;
		try {
			Query query;
			if (authority==Authority.CUSTOMER) {
				query = session.createQuery("from CommodityTypeVO vo where vo.authority=:authority").setParameter("authority", authority);
			} else {
				query = session.createQuery("from CommodityTypeVO");
			}
			//分頁
			list = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			list = new ArrayList<>();
		}
		return list;
	} 
	
	public CommodityTypeVO getByCommodityType (String commodityType) {
		List<CommodityTypeVO> list = getHelper(Restrictions.eq("commodityType", commodityType));
		if (list!=null && list.size()!=0) {
			return list.get(0);
		} else {
			return null;
		}
	} 

}
