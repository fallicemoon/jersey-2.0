package com.jersey.tools;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jersey.store.model.StoreService;
import com.jersey.tools.JerseyEnum.StoreType;

/**
 * Application Lifecycle Listener implementation class jerseyContextListener
 *
 */
public class jerseyContextListener implements ServletContextListener {

	private ApplicationContext applicationContext;
	
	private StoreService storeService;

	/**
	 * Default constructor.
	 */
	public jerseyContextListener() {

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
		storeService = (StoreService)applicationContext.getBean("storeService");
		// 塞入商店和託運公司
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.setAttribute(StoreType.store.toString(), storeService.getStoreSetByType(StoreType.store));
		servletContext.setAttribute(StoreType.shippingCompany.toString(), storeService.getStoreSetByType(StoreType.shippingCompany));
	}

}
