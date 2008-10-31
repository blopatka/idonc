package org.lopatka.idonc.web.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation("ADMIN")
public class UserDetailsPage extends BasePage {

	public UserDetailsPage(PageParameters params) {
		super(params);
		this.setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
			private static final long serialVersionUID = -6854691124950191170L;

			@Override
			protected Object load() {
				String username = getPageParameters().getString("username");
				IdoncSession session = IdoncSession.get();
				IdoncUser user = session.getUser(username);
				return user;
			}
		}));
		
		initLayout();
	}
	
	
	private void initLayout() {
		IdoncUser user = (IdoncUser) UserDetailsPage.this.getModelObject();
		
		Label username = new Label("username", user.getUserName());
		Label firstName = new Label("firstname", user.getFirstName());
		Label lastName = new Label("lastname", user.getLastName());
		Label email = new Label("email", user.getAddress().getEmail());
		Label website = new Label("website", user.getAddress().getWebsite());
		Label street = new Label("street", user.getAddress().getStreet());
		Label houseNumber = new Label("housenumber", user.getAddress().getHouseNumber());
		Label city = new Label("city", user.getAddress().getCity());
		Label zipCode = new Label("zipcode", user.getAddress().getZipCode());
		Label country = new Label("country", user.getAddress().getCountry());
		
		add(username);
		add(firstName);
		add(lastName);
		add(email);
		add(website);
		add(street);
		add(houseNumber);
		add(city);
		add(zipCode);
		add(country);
	}
}
