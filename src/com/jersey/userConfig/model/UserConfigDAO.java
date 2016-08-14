package com.jersey.userConfig.model;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.UserConfig;

@Repository
public class UserConfigDAO extends AbstractDAO<UserConfigVO> {

	public UserConfigDAO() {
		super(UserConfigVO.class, "userConfigId");
	}
	
	public void updateUserConfig (UserConfig userConfig, Object value) {
		//TODO 未來如果有新增其他帳戶的話這邊要加上where條件
		String hql = "update UserConfigVO u set u."+userConfig+"=:value";
		Session session = HibernateTools.getSession();
		session.beginTransaction();
		try {
			session.createQuery(hql).setParameter("value", value).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw e;
		}
	}
	

	
}
