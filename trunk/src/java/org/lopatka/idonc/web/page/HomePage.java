package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.lopatka.idonc.web.page.component.BasePage;

public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		initLayout();
	}
	
	private void initLayout() {		
		add(new Label("message","If you see this message WICKED is properly configured and running"));
	}
}
