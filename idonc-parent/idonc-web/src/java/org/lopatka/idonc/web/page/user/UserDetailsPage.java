package org.lopatka.idonc.web.page.user;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;

/**
 * @author Bartek
 */
@AuthorizeInstantiation("ADMIN")
public class UserDetailsPage extends BasePage {

    private static final long serialVersionUID = 1L;

	public UserDetailsPage(final PageParameters params) {
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
		
		initLayout();
	}
	
	private void initLayout() {
		
		IdoncUser user = (IdoncUser) UserDetailsPage.this.getModelObject();
		
		Form form = new Form("userForm");
		form.add(new Label("username", user.getUserName()));
		form.add(new Label("firstName", user.getFirstName()));
		form.add(new Label("lastName", user.getLastName()));
		form.add(new Label("email", user.getAddress().getEmail()));
		form.add(new Label("website", user.getAddress().getWebsite()));
		form.add(new Label("street", user.getAddress().getStreet()));
		form.add(new Label("houseNumber", user.getAddress().getHouseNumber()));
		form.add(new Label("city", user.getAddress().getCity()));
		form.add(new Label("zipCode", user.getAddress().getZipCode()));
		form.add(new Label("country", user.getAddress().getCountry()));
		form.add(new Button("backButton") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				setResponsePage(UserListPage.class);
			}
		});
		add(form);
	}
}

