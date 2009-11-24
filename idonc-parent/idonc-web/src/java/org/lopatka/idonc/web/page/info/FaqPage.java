package org.lopatka.idonc.web.page.info;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.lopatka.idonc.web.page.component.BasePage;

/**
 * @author Bartek
 */
@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.USER})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN})
public class FaqPage extends BasePage {

    private static final long serialVersionUID = 1L;

	public FaqPage() {
		initLayout();
	}

	public void initLayout() {
	}

}

