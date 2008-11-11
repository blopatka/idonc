package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;

/**
 * @author Bartek
 */
@AuthorizeInstantiation("ADMIN")
public class UserDetailsPage extends BasePage {

	public UserDetailsPage(final PageParameters params) {
		super(params);
		
		this.setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
			private static final long serialVersionUID = -6854691124950191170L;

			@Override
			protected Object load() {
				String username = getPageParameters().getString("username");
				IdoncSession session = IdoncSession.get();
				return session.getUser(username);
			}
		}));
		
		initLayout();
	}
	
	private void initLayout() {
		
		IdoncUser user = (IdoncUser) UserDetailsPage.this.getModelObject();
		
		add(new Label("username", user.getUserName()));
		add(new Label("firstName", user.getFirstName()));
		add(new Label("lastName", user.getLastName()));
		add(new Label("email", user.getAddress().getEmail()));
		add(new Label("website", user.getAddress().getWebsite()));
		add(new Label("street", user.getAddress().getStreet()));
		add(new Label("houseNumber", user.getAddress().getHouseNumber()));
		add(new Label("city", user.getAddress().getCity()));
		add(new Label("zipCode", user.getAddress().getZipCode()));
		add(new Label("country", user.getAddress().getCountry()));
		add(new Button("backButton") {
			private static final long serialVersionUID = -6744445235406140825L;

			@Override
			public void onSubmit() {
				setResponsePage(UserListPage.class);
			}
		});
	}
}

