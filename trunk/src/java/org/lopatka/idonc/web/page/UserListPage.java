package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.dataproviders.UserDataProvider;

/**
 * @author Bartek
 */
@AuthorizeInstantiation("ADMIN")
public class UserListPage extends BasePage {

	@SpringBean(name = "userDao")
	private UserDao dao;
	
	private IdoncSession session = IdoncSession.get();

	public UserListPage() {
		initLayout();
	}

	private void initLayout() {	
		UserDataProvider prov = new UserDataProvider(dao);
		DataView table = new DataView("pageable", prov) {
			private static final long serialVersionUID = 6733451812819095983L;

			@Override
			protected void populateItem(final Item item) {
				IdoncUser user = (IdoncUser) item.getModelObject();
				item.add(new Label("username", user.getUserName()));
				item.add(new Label("firstname", user.getFirstName()));
				item.add(new Label("lastname", user.getLastName()));
				item.add(new Label("email", user.getAddress().getEmail()));
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
