package com.jersey.commodity.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;

@Repository
public class CommodityAttrMappingDAO extends AbstractDAO<CommodityAttrMappingVO> {

	@Autowired
	private HibernateTools hibernateTools;
	
	public CommodityAttrMappingDAO() {
		super(CommodityAttrMappingVO.class, "commodityAttrMappingId");
	}
	
	public List<CommodityAttrMappingVO> getByCommodityVO (CommodityVO commodityVO) {
		return getHelper(Restrictions.eq("commodityVO", commodityVO));
	}
	
	public List<CommodityAttrMappingVO> getByCommodityVO (List<CommodityVO> commodityList) {
		return getHelper(Restrictions.in("commodityVO", commodityList));
	}
	
	public void create (List<CommodityAttrMappingVO> list) {
		Session session = hibernateTools.getSession();
		session.beginTransaction();
		try {
			for (int i = 0; i < list.size(); i++) {
				session.save(list.get(i));
				if ((i+1)%20==0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

}
