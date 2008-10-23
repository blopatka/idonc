package org.lopatka.idonc.web.page.component;

import org.lopatka.idonc.web.page.AuthenticatedPage;

/**
 * @author Bartek
 */
public abstract class BasePage extends AuthenticatedPage {

	public BasePage() {
		super();
		add(new HeaderPanel("header"));
		add(new FooterPanel("footer"));
	}
	

}

