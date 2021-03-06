package org.lopatka.idonc.web.page.project;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.USER})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN})
public class SetActiveProjectPage extends BasePage {

	@SpringBean(name="idoncService")
	private IdoncService idoncService;

	private IdoncSession session = IdoncSession.get();

	public SetActiveProjectPage(PageParameters params) {
		super(params);
		this.setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

			private static final long serialVersionUID = -2720398180900894289L;

			@Override
			protected Object load() {
				String projectName = getPageParameters().getString("projectname");
				IdoncSession session = IdoncSession.get();
				return session.getProject(projectName);
			}
		}));
		makeProjectAsActive();
		initLayout();
	}

	private void makeProjectAsActive() {
		IdoncProject project = (IdoncProject) SetActiveProjectPage.this.getModelObject();
		IdoncUser user = idoncService.setContributedProject(session.getLoggedUserName(), project,  session.getSessionId());
		LoggedUser lU = session.getLoggedUser();
		lU.setUser(user);
		session.setLoggedUser(lU);
		session.setUser(user.getUserName(), user);
	}

	private void initLayout() {
		IdoncProject project = (IdoncProject) SetActiveProjectPage.this.getModelObject();
		this.add(new Label("user", session.getLoggedUserName()));
		this.add(new Label("project", project.getName()));
	}

}
