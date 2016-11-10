package com.jersey.tools;

import java.sql.Blob;
import java.sql.Clob;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateTools {
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	private static ServiceRegistry serviceRegistry;

//	static {
//		Configuration configuration = new Configuration().configure();
//		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
//	            configuration.getProperties()).build();
//		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Blob getBlob (byte[] bytes) {
		return Hibernate.getLobCreator(getSession()).createBlob(bytes);
	} 
	
	public Clob getClob (String string) {
		return Hibernate.getLobCreator(getSession()).createClob(string);
	}
	
	
}
