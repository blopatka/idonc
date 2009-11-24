package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.USER})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN})
public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		initLayout();
	}

	private void initLayout() {
		//add(new Label("message",new ResourceModel("homePage.message")));
	}
}
