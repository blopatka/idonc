package org.lopatka.idonc.web;

import java.net.MalformedURLException;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.lopatka.idonc.web.page.LoginPage;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IdoncApplication extends BaseIdoncApplication {

	@Override
	protected void init() {
		super.init();
		
		getMarkupSettings().setCompressWhitespace(true);
		getMarkupSettings().setStripComments(true);
		getMarkupSettings().setStripWicketTags(true);
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");

	}
	
	@Override
	public ApplicationContext context() {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}

	@Override
	protected Object getHiveKey() {
		return getServletContext().getContextPath();
	}

	@Override
	protected void setUpHive() {
		PolicyFileHiveFactory factory = new PolicyFileHiveFactory();
		try {
			factory.addPolicyFile(getServletContext().getResource("WEB-INF/idonclogin.hive"));
			factory.setAlias("hp", "org.lopatka.idonc.web.page.HomePage");
		} 
		catch (MalformedURLException e) {
			throw new WicketRuntimeException(e);
		}
		HiveMind.registerHive(getHiveKey(), factory);
		
	}

	public Class getLoginPage() {
		return LoginPage.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new IdoncSession(this, request);
	}

}
