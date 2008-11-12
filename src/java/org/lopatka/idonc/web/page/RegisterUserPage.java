package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
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
		form.add(new TextField("usernameInput", new PropertyModel(this, "user.userName")));
		form.add(new PasswordTextField("passwordInput", new PropertyModel(this, "password")));
		form.add(new PasswordTextField("rePasswordInput", new PropertyModel(this, "rePassword")));
		form.add(new TextField("firstNameInput", new PropertyModel(this, "user.firstName")));
		form.add(new TextField("lastNameInput", new PropertyModel(this, "user.lastName")));
		form.add(new TextField("emailInput", new PropertyModel(this, "user.address.email")));
		form.add(new TextField("websiteInput", new PropertyModel(this, "user.address.website")));
		form.add(new TextField("streetInput", new PropertyModel(this, "user.address.street")));
		form.add(new TextField("houseNumberInput", new PropertyModel(this, "user.address.houseNumber")));
		form.add(new TextField("cityInput", new PropertyModel(this, "user.address.city")));
		form.add(new TextField("zipCodeInput", new PropertyModel(this, "user.address.zipCode")));
		form.add(new TextField("countryInput", new PropertyModel(this, "user.address.country")));

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

		this.add(form);
	}
}

