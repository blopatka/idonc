package org.lopatka.idonc.web;

import java.net.MalformedURLException;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.lopatka.idonc.web.page.AuthenticatedPage;
import org.lopatka.idonc.web.page.HomePage;
import org.lopatka.idonc.web.page.LoginPage;
import org.lopatka.idonc.web.utils.IdoncLoginContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IdoncApplication extends SwarmWebApplication {

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this, context()));
		
		getMarkupSettings().setCompressWhitespace(true);
		getMarkupSettings().setStripComments(true);
		getMarkupSettings().setStripWicketTags(true);
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");

	}
	
	public ApplicationContext context() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}

	@Override
	protected Object getHiveKey() {
//		return getServletContext().getContextPath();
		return "idonc-applications";
	}

	@Override
	protected void setUpHive() {
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory();
		try {
			factory.addPolicyFile(getServletContext().getResource("/WEB-INF/idonclogin.hive"));
			factory.setAlias("hp", "org.lopatka.idonc.web.page.HomePage");
			factory.setAlias("package", "org.lopatka.idonc.web");
		} 
		catch (MalformedURLException e) {
			throw new WicketRuntimeException(e);
		}
		HiveMind.registerHive(getHiveKey(), factory);
		
	}

	@Override
	public Class getHomePage() {
		return HomePage.class;
		//return AuthenticatedPage.class;
	}
	
	public Class getLoginPage() {
		return LoginPage.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new IdoncSession(this, request);
	}
	
	public LoginContext getLogoffContext()
	{
		return new IdoncLoginContext();
	}

}
