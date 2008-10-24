package org.lopatka.idonc.web.page;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.lopatka.idonc.web.IdoncApplication;

/**
 * @author Bartek
 */
public class AuthenticatedPage extends SecureWebPage {

	public AuthenticatedPage() {
		super();
		add(new Label("label", new Model("label label")));
	}
	
	protected final WaspSession getSecureSession() {
		return (WaspSession)Session.get();
	}
	
	protected final SwarmWebApplication getSecureApplication() {
		return (SwarmWebApplication)Application.get();
	}
	
	protected final LoginContext getLogoffContext() {
		Application app = Application.get();
		if (app instanceof IdoncApplication) {
			((IdoncApplication)app).getLogoffContext();
		}
		throw new WicketRuntimeException("Application is not a subclass of"+ IdoncApplication.class);
	}
}

