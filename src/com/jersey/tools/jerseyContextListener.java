package com.jersey.tools;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jersey.store.model.StoreService;
import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;
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
		//TODO cache
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.setAttribute("store", storeService.getStoreSetByType(StoreType.STORE));
		servletContext.setAttribute("shippingCompany", storeService.getStoreSetByType(StoreType.SHIPPING_COMPANY));
		servletContext.setAttribute("authority", Authority.values());
		servletContext.setAttribute("commodityAttrAuthority", CommodityAttrAuthority.values());
		// 塞入使用者設定
//		UserConfigWithJsonVO userConfigWithJsonVO = userConfigService.getAll().get(0);
//		servletContext.setAttribute(UserConfig.commodityPageSize.toString(), userConfigWithJsonVO.getCommodityPageSize());
//		servletContext.setAttribute(UserConfig.purchaseCasePageSize.toString(), userConfigWithJsonVO.getPurchaseCasePageSize());
//		servletContext.setAttribute(UserConfig.sellCasePageSize.toString(), userConfigWithJsonVO.getSellCasePageSize());
//		servletContext.setAttribute(UserConfig.storePageSize.toString(), userConfigWithJsonVO.getStorePageSize());
//		servletContext.setAttribute(UserConfig.commodityAttrJson.toString(), userConfigWithJsonVO.getCommodityAttrJson());
	}

}
