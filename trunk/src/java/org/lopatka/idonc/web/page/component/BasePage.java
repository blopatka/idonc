package org.lopatka.idonc.web.page.component;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.security.components.ISecurePage;

/**
 * @author Bartek
 */
public abstract class BasePage extends WebPage implements ISecurePage {

	public BasePage() {
		super();
		add(new HeaderPanel("header"));
		add(new FooterPanel("footer"));
	}
	

}

