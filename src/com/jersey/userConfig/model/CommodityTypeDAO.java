package com.jersey.userConfig.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.Authority;

@Repository
public class CommodityTypeDAO extends AbstractDAO<CommodityTypeVO> {

	public CommodityTypeDAO() {
		super(CommodityTypeVO.class, "commodityTypeId");
	}
	
	public List<CommodityTypeVO> getAll () {
		Session session = HibernateTools.getSession();
		session.getTransaction().begin();
		List<CommodityTypeVO> list;
		try {
			
			Query query = session.createQuery("from CommodityTypeVO");
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

}
