package org.lopatka.idonc.web.page.admin;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation(value = {Roles.ADMIN})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.ADMIN})
public class UserDeletePage extends BasePage{

	@SpringBean(name="idoncService")
	private IdoncService idoncService;

	private IdoncSession session = IdoncSession.get();

	public UserDeletePage(PageParameters params) {
		super(params);
		this.setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Object load() {
				String username = getPageParameters().getString("username");
				IdoncSession session = IdoncSession.get();
				return session.getUser(username);
			}

		}));
		deleteUser();
		initLayout();
	}

	private void deleteUser() {
		IdoncUser delUser = (IdoncUser) UserDeletePage.this.getModelObject();
		session.removeUser(delUser.getUserName());
		idoncService.deleteUser(delUser, session.getLoggedUserName(), session.getSessionId());
	}

	private void initLayout() {


	}
}
