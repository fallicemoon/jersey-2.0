package com.jersey.tools;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.jersey.store.model.StoreService;
import com.jersey.tools.JerseyEnum.StoreType;

/**
 * Application Lifecycle Listener implementation class jerseyContextListener
 *
 */
public class jerseyContextListener implements ServletContextListener {

	@Autowired
	private StoreService storeService;

	/**
	 * Default constructor.
	 */
	public jerseyContextListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// 塞入商店和託運公司
//		ServletContext servletContext = servletContextEvent.getServletContext();
//		servletContext.setAttribute(StoreType.store.toString(), storeService.getStoreListByType(StoreType.store));
//		servletContext.setAttribute(StoreType.shippingCompany.toString(), storeService.getStoreListByType(StoreType.shippingCompany));
	}

}
