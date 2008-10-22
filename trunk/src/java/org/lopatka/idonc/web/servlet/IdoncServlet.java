package org.lopatka.idonc.web.servlet;

import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IdoncServlet { 
//extends WebContainerServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6743495386106463223L;

	private ApplicationContext context;
	private String applicationName;
	private Logger log = Logger.getLogger(this.getClass().getName());

//	@Override
//	public ApplicationInstance newApplicationInstance() {
//		return new IdoncApplication();
//	}

	protected ApplicationContext getApplicationContext() {
		return context;
	}

//	public void init() throws ServletException {
//		super.init();
//
//		context = WebApplicationContextUtils
//				.getWebApplicationContext(getServletContext());
//
//		applicationName = getUniqueInstanceName(ApplicationInstance.class);
//
//	}

	private String getUniqueInstanceName(Class clazz) {
		String[] beansNames = context.getBeanNamesForType(clazz);
		if (beansNames == null || beansNames.length == 0) {
			throw new IllegalArgumentException("A bean of type "
					+ clazz.getName()
					+ " must be declared in the application context.");
		}
		if (beansNames.length > 1) {
			throw new IllegalArgumentException(
					"There must be only one bean of type " + clazz.getName()
							+ " declared in the application context.");
		}
		return beansNames[0];
	}
	
	

}
