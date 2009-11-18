package org.lopatka.idonc.web.page.user;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.lopatka.idonc.model.user.Address;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation("ADMIN")
public class EditUserPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public EditUserPage() {
		initLayout();
	}

	private void initLayout() {
		add(new EditUserForm("editUserForm"));
	}

	private class EditUserForm extends Form {

		private static final long serialVersionUID = 1L;

		@SpringBean(name = "idoncService")
		private IdoncService idoncService;

		private IdoncUser editedUser;

		private PasswordTextField oldPasswordInput;
		private PasswordTextField passwordInput;
		private PasswordTextField rePasswordInput;
		private TextField firstNameInput;
		private TextField lastNameInput;
		private TextField emailInput;
		private TextField websiteInput;
		private TextField streetInput;
		private TextField houseNumberInput;
		private TextField cityInput;
		private TextField zipCodeInput;
		private TextField countryInput;

		public EditUserForm(String id) {
			super(id);
			editedUser = IdoncSession.get().getLoggedUser().getUser();

			add(new Label("usernameInput", new PropertyModel(this, "editedUser.userName")));

			oldPasswordInput = new PasswordTextField("oldPasswordInput", new Model(""));
			oldPasswordInput.add(StringValidator.minimumLength(6));
			oldPasswordInput.setRequired(true);
			oldPasswordInput.setLabel(new ResourceModel("userEdit.oldPassword.label"));
			add(oldPasswordInput);

			passwordInput = new PasswordTextField("passwordInput", new Model(""));
			passwordInput.setLabel(new ResourceModel("userEdit.password.label"));
			passwordInput.add(StringValidator.minimumLength(6));
			passwordInput.setRequired(false);
			add(passwordInput);

			rePasswordInput = new PasswordTextField("rePasswordInput", new Model(""));
			rePasswordInput.setLabel(new ResourceModel("userEdit.rePassword.label"));
			rePasswordInput.add(StringValidator.minimumLength(6));
			rePasswordInput.setRequired(false);
			add(rePasswordInput);

			firstNameInput = new TextField("firstNameInput", new PropertyModel(this, "editedUser.firstName"));
			add(firstNameInput);

			lastNameInput = new TextField("lastNameInput", new PropertyModel(this, "editedUser.lastName"));
			add(lastNameInput);

			emailInput = new TextField("emailInput", new PropertyModel(this, "editedUser.address.email"));
			emailInput.setRequired(true);
			emailInput.add(EmailAddressValidator.getInstance());
			add(emailInput);

			websiteInput = new TextField("websiteInput", new PropertyModel(this, "editedUser.address.website"));
			add(websiteInput);

			streetInput = new TextField("streetInput", new PropertyModel(this, "editedUser.address.street"));
			add(streetInput);

			houseNumberInput = new TextField("houseNumberInput", new PropertyModel(this, "editedUser.address.houseNumber"));
			add(houseNumberInput);

			cityInput = new TextField("cityInput", new PropertyModel(this, "editedUser.address.city"));
			add(cityInput);

			zipCodeInput = new TextField("zipCodeInput", new PropertyModel(this, "editedUser.address.zipCode"));
			add(zipCodeInput);

			countryInput = new TextField("countryInput", new PropertyModel(this, "editedUser.address.country"));
			add(countryInput);

			add(new FeedbackPanel("feedback"));

			this.add(new Button("acceptButton") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					if(checkNewPasswords(passwordInput.getInput(), rePasswordInput.getInput())) {
						String password = oldPasswordInput.getInput();
						if(passwordInput.getInput() != "" && rePasswordInput.getInput() != "") {
							//zmiana hasła
							boolean result = idoncService.changePassword(editedUser, oldPasswordInput.getInput(), passwordInput.getInput());
							if(!result) {
								error(this.getParent().getString("userEdit.changePasswordFailed"));
							} else {
								password = passwordInput.getInput();
							}
						}

						//edycja użytkownika
						editedUser.setFirstName(firstNameInput.getInput());
						editedUser.setLastName(lastNameInput.getInput());
						Address address = editedUser.getAddress();
						if (address == null) {
							address = new Address();
						}
						address.setEmail(emailInput.getInput());
						address.setWebsite(websiteInput.getInput());
						address.setStreet(streetInput.getInput());
						address.setHouseNumber(houseNumberInput.getInput());
						address.setCity(cityInput.getInput());
						address.setZipCode(zipCodeInput.getInput());
						address.setCountry(countryInput.getInput());
						editedUser.setAddress(address);
						boolean editResult = idoncService.editUser(editedUser, password);
						if(!editResult) {
							error(this.getParent().getString("userEdit.editFailed"));
						} else {
							setResponsePage(UserListPage.class);
						}
					}

				}

			});

			this.add(new Button("backButton") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					setResponsePage(UserListPage.class);
				}
			});

		}

		private Boolean checkNewPasswords(String password, String rePassword) {
			if(password != "" && rePassword != "") {
				 //oba hasła wypełnione
				if(!password.equals(rePassword)) {
					error(this.getParent().getString("userEdit.passwordsNotEqual"));
					return Boolean.FALSE;
				}
			} else {
				if(!(password == "" && rePassword == "")) {
					//jedno z hasel puste
					error(this.getParent().getString("userEdit.passwordsEmptyOrFilled"));
					return Boolean.FALSE;
				}
			}

			return Boolean.TRUE;
		}

	}
}
