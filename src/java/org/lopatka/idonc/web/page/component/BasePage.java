package org.lopatka.idonc.web.page.component;

import org.apache.wicket.markup.html.panel.Panel;
import org.lopatka.idonc.web.page.AuthenticatedPage;

/**
 * @author Bartek
 */
public abstract class BasePage extends AuthenticatedPage {

	@Override
	protected void onBeforeRender() {
		init();
		super.onBeforeRender();
	}

	private void init() {
		addOrReplace(new HeaderPanel("header"));
		addOrReplace(getContentPanel("content"));
		addOrReplace(new FooterPanel("footer"));
	}

	public abstract Panel getContentPanel(String id);
}

