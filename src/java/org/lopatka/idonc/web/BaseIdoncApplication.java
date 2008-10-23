package org.lopatka.idonc.web;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.lopatka.idonc.web.page.LoginPage;
import org.springframework.context.ApplicationContext;

public abstract class BaseIdoncApplication extends WebApplication {

	@Override
	public Class getHomePage() {
		return LoginPage.class;
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this, context()));
	}

	public abstract ApplicationContext context();
}
