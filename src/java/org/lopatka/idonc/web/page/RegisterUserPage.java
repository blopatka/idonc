package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.lopatka.idonc.web.model.user.IdoncUser;

/**
 * @author Bartek
 */
public class RegisterUserPage extends WebPage {

	IdoncUser user;
	String rePassword;

	public RegisterUserPage() {
		Form form = new Form("registerForm");
		form.add(new TextArea("usernameInput", new PropertyModel(this, "user.userName")));
		form.add(new TextArea("passwordInput", new PropertyModel(this, "user.password")));
		form.add(new TextArea("rePasswordInput", new PropertyModel(this, "rePassword")));
		form.add(new TextArea("firstNameInput", new PropertyModel(this, "user.firstName")));
		form.add(new TextArea("lastNameInput", new PropertyModel(this, "user.lastName")));
		form.add(new TextArea("emailInput", new PropertyModel(this, "user.address.email")));
		form.add(new TextArea("websiteInput", new PropertyModel(this, "user.address.website")));
		form.add(new TextArea("streetInput", new PropertyModel(this, "user.address.street")));
		form.add(new TextArea("houseNumberInput", new PropertyModel(this, "user.address.houseNumber")));
		form.add(new TextArea("cityInput", new PropertyModel(this, "user.address.city")));
		form.add(new TextArea("zipCodeInput", new PropertyModel(this, "user.address.zipCode")));
		form.add(new TextArea("countryInput", new PropertyModel(this, "user.address.country")));

		Button accept = new Button("acceptButton") {
			@Override
			public void onSubmit() {
				//TODO - register user as login as him, redirect to home page
				setResponsePage(HomePage.class);
			}
		};
		form.add(accept);

		Button cancel = new Button("cancelButton") {
			@Override
			public void onSubmit() {
				setResponsePage(LoginPage.class);
			}
		};
		form.add(cancel);

		add(form);
	}
}

