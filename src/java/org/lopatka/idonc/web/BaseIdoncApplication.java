package org.lopatka.idonc.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.lopatka.idonc.web.page.HomePage;
import org.springframework.context.ApplicationContext;

public abstract class BaseIdoncApplication extends WebApplication {

	@Override
	public Class getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		
	}

	public abstract ApplicationContext context();
}
