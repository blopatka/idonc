package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.ISecurityCheck;
import org.lopatka.idonc.web.page.component.BasePage;

public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		initLayout();
	}
	
	private void initLayout() {		
		add(new Label("message","If you see this message WICKED is properly configured and running"));
	}

	public ISecurityCheck getSecurityCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isActionAuthorized(String waspAction) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isActionAuthorized(WaspAction action) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setSecurityCheck(ISecurityCheck check) {
		// TODO Auto-generated method stub
		
	}
}
