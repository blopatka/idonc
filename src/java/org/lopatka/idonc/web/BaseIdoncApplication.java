package org.lopatka.idonc.web;

import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.lopatka.idonc.web.page.HomePage;
import org.springframework.context.ApplicationContext;

public abstract class BaseIdoncApplication extends SwarmWebApplication {

	@Override
	public Class getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this, context()));
	}

	public abstract ApplicationContext context();
}
