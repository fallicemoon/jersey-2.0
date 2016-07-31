package tools;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import store.model.StoreService;
import tools.JerseyEnum.StoreType;

/**
 * Application Lifecycle Listener implementation class jerseyContextListener
 *
 */
public class jerseyContextListener implements ServletContextListener {

	private StoreService storeService = new StoreService();

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
		ServletContext servletContext = servletContextEvent.getServletContext();
		servletContext.setAttribute(StoreType.store.toString(), storeService.getStoreListByType(StoreType.store));
		servletContext.setAttribute(StoreType.shippingCompany.toString(), storeService.getStoreListByType(StoreType.shippingCompany));
	}

}
