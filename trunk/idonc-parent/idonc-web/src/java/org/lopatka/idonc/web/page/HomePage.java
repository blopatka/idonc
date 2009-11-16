package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation("ADMIN")
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		initLayout();
	}

	private void initLayout() {
		add(new Label("message",new ResourceModel("homePage.message")));
	}
}
