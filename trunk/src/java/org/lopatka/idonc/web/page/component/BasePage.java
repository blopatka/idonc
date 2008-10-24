package org.lopatka.idonc.web.page.component;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.lopatka.idonc.web.IdoncApplication;

/**
 * @author Bartek
 */
public abstract class BasePage extends SecureWebPage {
//public abstract class BasePage extends WebPage {

	public BasePage() {
		init();
	}
	
	public BasePage(PageParameters parameters) {
		super(parameters);
		init();
	}
	
	public BasePage(IModel model) {
		super(model);
		init();
	}
	
	private void init() {
		Link logoff = new Link("logoff") {
			private static final long serialVersionUID = 3035029090055221654L;

			@Override
			public void onClick() {
				WaspSession waspSession = ((WaspSession)getSession());
				if(waspSession.logoff(getLogoffContext())) {
					//TODO - a nie LoginPage?
					setResponsePage(Application.get().getHomePage());
					waspSession.invalidate();
				} else {
					error("A problem occured during the logoff process, please try again or contact support");
				}
			}
			
		};
		add(logoff);
		add(new HeaderPanel("header"));
		add(new FooterPanel("footer"));
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

