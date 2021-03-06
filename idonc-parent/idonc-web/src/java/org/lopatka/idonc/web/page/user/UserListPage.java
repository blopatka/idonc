package org.lopatka.idonc.web.page.user;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.dataproviders.UserDataProvider;
import org.lopatka.idonc.service.IdoncService;

/**
 * @author Bartek
 */
@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.USER})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN})
public class UserListPage extends BasePage {
    private static final long serialVersionUID = 1L;

	@SpringBean(name = "idoncService")
	private IdoncService idoncService;

	private IdoncSession session = IdoncSession.get();

	public UserListPage() {
		initLayout();
	}

	private void initLayout() {
		BookmarkablePageLink edit = new BookmarkablePageLink("edit", EditUserPage.class);
        add(edit);

        UserDataProvider prov = new UserDataProvider(idoncService);
		DataView table = new DataView("pageable", prov) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item item) {
				IdoncUser user = (IdoncUser) item.getModelObject();
				item.add(new Label("username", user.getUserName()));
				item.add(new Label("firstname", user.getFirstName()));
				item.add(new Label("lastname", user.getLastName()));
				if (user.getAddress() != null) {
					item.add(new Label("email", user.getAddress().getEmail()));
				} else {
					item.add(new Label("email", ""));
				}
				PageParameters param = new PageParameters();
				param.add("username", user.getUserName());
				item.add(new BookmarkablePageLink("details", UserDetailsPage.class, param));

				//persisting in session
				session.setUser(user.getUserName(), user);
			}
		};

		table.setItemsPerPage(10);
		add(table);
		add(new PagingNavigator("navigator", table));
	}
}
