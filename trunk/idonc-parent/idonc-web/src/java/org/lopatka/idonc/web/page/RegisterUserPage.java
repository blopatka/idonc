package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.hibernate.validator.LengthValidator;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;

/**
 * @author Bartek
 */
public class RegisterUserPage extends WebPage {

	private static final long serialVersionUID = 1L;

	private TextField usernameField;
	private PasswordTextField passwordField;
	private PasswordTextField rePasswordField;
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField emailField;
	private TextField websiteField;
	private TextField streetField;
	private TextField houseNumberField;
	private TextField cityField;
	private TextField zipCodeField;
	private TextField countryField;

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

			usernameField = new TextField("usernameInput", new PropertyModel(this, "user.userName"));
			usernameField.setRequired(true);
			usernameField.setLabel(new ResourceModel("register.username.label"));
			usernameField.add(StringValidator.minimumLength(6));
			add(usernameField);

			passwordField = new PasswordTextField("passwordInput", new PropertyModel(this, "password"));
			passwordField.setRequired(true);
			passwordField.setLabel(new ResourceModel("register.password.label"));
			passwordField.add(StringValidator.minimumLength(6));
			add(passwordField);

			rePasswordField = new PasswordTextField("rePasswordInput", new PropertyModel(this, "rePassword"));
			rePasswordField.setRequired(true);
			rePasswordField.setLabel(new ResourceModel("register.rePassword.label"));
			rePasswordField.add(StringValidator.minimumLength(6));
			add(rePasswordField);

			firstNameField = new TextField("firstNameInput", new PropertyModel(this, "user.firstName"));
			add(firstNameField);

			lastNameField = new TextField("lastNameInput", new PropertyModel(this, "user.lastName"));
			add(lastNameField);

			emailField = new TextField("emailInput", new PropertyModel(this, "user.address.email"));
			emailField.setRequired(true);
			emailField.setLabel(new ResourceModel("register.email.label"));
			emailField.add(EmailAddressValidator.getInstance());
			add(emailField);

			websiteField = new TextField("websiteInput", new PropertyModel(this, "user.address.website"));
			add(websiteField);

			streetField = new TextField("streetInput", new PropertyModel(this, "user.address.street"));
			add(streetField);

			houseNumberField = new TextField("houseNumberInput", new PropertyModel(this, "user.address.houseNumber"));
			add(houseNumberField);

			cityField = new TextField("cityInput", new PropertyModel(this, "user.address.city"));
			add(cityField);

			zipCodeField = new TextField("zipCodeInput", new PropertyModel(this, "user.address.zipCode"));
			add(zipCodeField);

			countryField = new TextField("countryInput", new PropertyModel(this, "user.address.country"));
			add(countryField);

			add(new FeedbackPanel("feedback").setOutputMarkupId(true));

			this.add( new Button("acceptButton") {
				private static final long serialVersionUID = -4742823374395732L;

				@Override
				public void onSubmit() {
					if(checkPasswordsEquals(password, rePassword) && checkUsernameNotExist(usernameField)) {
						boolean result = idoncService.registerUser(user, password);
						if(!result) {
							error(this.getParent().getString("register.errorDuringRegister"));
						} else {
							setResponsePage(HomePage.class);
						}
					}
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

		private Boolean checkPasswordsEquals(String password, String rePassword) {
			if(!password.equals(rePassword)) {
				error(this.getParent().getString("register.passwordsNotEqual"));
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

		private Boolean checkUsernameNotExist(TextField username) {
			Boolean result = idoncService.checkUserExists(user.getUserName());
			if(!result) {
				error(this.getParent().getString("register.usernameExists"));
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
	}
}

