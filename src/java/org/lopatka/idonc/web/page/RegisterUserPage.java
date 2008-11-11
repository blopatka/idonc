package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.service.IdoncService;

/**
 * @author Bartek
 */
public class RegisterUserPage extends WebPage {

	@SpringBean(name="idoncService")
	private IdoncService idoncService;
	
	private IdoncUser user;
	private String password;
	private String rePassword;

	public RegisterUserPage() {
		Form form = new Form("registerForm");
		form.add(new TextArea("usernameInput", new PropertyModel(this, "user.userName")));
		form.add(new TextArea("passwordInput", new PropertyModel(this, "password")));
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

			private static final long serialVersionUID = -4742823374395732L;

			@Override
			public void onSubmit() {
				//TODO check if username exists
				if(password.equals(rePassword)) {
					idoncService.registerUser(user, password);
				}
				setResponsePage(HomePage.class);
			}
		};
		form.add(accept);

		Button cancel = new Button("cancelButton") {

			private static final long serialVersionUID = 6703091768251015771L;

			@Override
			public void onSubmit() {
				setResponsePage(LoginPage.class);
			}
		};
		form.add(cancel);

		add(form);
	}
}

