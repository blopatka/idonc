package org.lopatka.idonc.web.page;

import org.apache.wicket.RequestCycle;
import org.lopatka.idonc.web.page.component.BasePage;

/**
 * @author Bartek
 */
public class LogoutPage extends BasePage {

    private static final long serialVersionUID = 1L;

	public LogoutPage() {
		getSession().invalidate();
		RequestCycle.get().setResponsePage(LoginPage.class);
		setRedirect(true);
	}
}

