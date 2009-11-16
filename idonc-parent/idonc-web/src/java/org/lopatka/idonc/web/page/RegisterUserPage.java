package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;

/**
 * @author Bartek
 */
public class RegisterUserPage extends WebPage {

	private static final long serialVersionUID = 1L;



	public RegisterUserPage() {
		add(new RegisterUserForm("registerForm"));
	}

	private class RegisterUserForm extends Form {

		private static final long serialVersionUID = 1L;

		@SpringBean(name="idoncService")
		private IdoncService idoncService;

		private IdoncUser user;
		private String password;
		private String rePassword;

		public RegisterUserForm(String id) {
			super(id);
			add(new TextField("usernameInput", new PropertyModel(this, "user.userName")));
			add(new PasswordTextField("passwordInput", new PropertyModel(this, "password")));
			add(new PasswordTextField("rePasswordInput", new PropertyModel(this, "rePassword")));
			add(new TextField("firstNameInput", new PropertyModel(this, "user.firstName")));
			add(new TextField("lastNameInput", new PropertyModel(this, "user.lastName")));
			add(new TextField("emailInput", new PropertyModel(this, "user.address.email")));
			add(new TextField("websiteInput", new PropertyModel(this, "user.address.website")));
			add(new TextField("streetInput", new PropertyModel(this, "user.address.street")));
			add(new TextField("houseNumberInput", new PropertyModel(this, "user.address.houseNumber")));
			add(new TextField("cityInput", new PropertyModel(this, "user.address.city")));
			add(new TextField("zipCodeInput", new PropertyModel(this, "user.address.zipCode")));
			add(new TextField("countryInput", new PropertyModel(this, "user.address.country")));

			this.add( new Button("acceptButton") {
				private static final long serialVersionUID = -4742823374395732L;

				@Override
				public void onSubmit() {
					//FIXME check if username exists
					if(password.equals(rePassword)) {
						idoncService.registerUser(user, password);
					}
					setResponsePage(HomePage.class);
				}
			});
			Button backButton = new Button("backButton") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					setResponsePage(HomePage.class);
				}
			};
			backButton.setDefaultFormProcessing(false);
			this.add(backButton);
		}
	}
}

