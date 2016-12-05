package com.jersey.systemParam.model;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.PrimaryKey;

@Repository
public class SystemParamDAO extends AbstractDAO<SystemParamVO> {
	
	@Autowired
	private HibernateTools hibernateTools;

	public SystemParamDAO(Class<SystemParamVO> type, String pk) {
		super(SystemParamVO.class, "name");
	}
	
	//TODO
	public SystemParamVO getPrimaryKeyValueAndUpdate (PrimaryKey primaryKey) {
		Session session = hibernateTools.getSession();
		session.beginTransaction();
		try {
			SystemParamVO vo = (SystemParamVO) session.get(SystemParamVO.class, primaryKey);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
	} 

}
