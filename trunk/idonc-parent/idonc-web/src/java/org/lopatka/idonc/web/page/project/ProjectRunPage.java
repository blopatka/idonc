package org.lopatka.idonc.web.page.project;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.ResourceModel;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.USER})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN})
public class ProjectRunPage extends BasePage {
	private static final long serialVersionUID = 1L;

	public ProjectRunPage() {
		initLayout();
	}

	private void initLayout() {
		this.add(new ExternalLink("webstart", new ResourceModel("webstart.link")));
	}

}
