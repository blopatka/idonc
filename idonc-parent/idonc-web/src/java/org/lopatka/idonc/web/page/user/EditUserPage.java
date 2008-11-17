package org.lopatka.idonc.web.page.user;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.service.IdoncService;

@AuthorizeInstantiation("ADMIN")
public class EditUserPage extends BasePage{

	@SpringBean(name="idoncService")
	private IdoncService idoncService;
	
	private IdoncUser editedUser;
	private String oldPassword;
	private String password;
	private String rePassword;
	
	public EditUserPage() {
		
		editedUser = IdoncSession.get().getLoggedUser().getUser();
		
		initLayout();
	}
	
	private void initLayout() {
		Form form = new Form("editUserForm");
		form.add(new Label("usernameInput", new PropertyModel(this, "editedUser.userName")));
		form.add(new PasswordTextField("oldPasswordInput", new PropertyModel(this, "oldPassword")));
		form.add(new PasswordTextField("passwordInput", new PropertyModel(this, "password")));
		form.add(new PasswordTextField("rePasswordInput", new PropertyModel(this, "rePassword")));
		form.add(new TextField("firstNameInput", new PropertyModel(this, "editedUser.firstName")));
		form.add(new TextField("lastNameInput", new PropertyModel(this, "editedUser.lastName")));
		form.add(new TextField("emailInput", new PropertyModel(this, "editedUser.address.email")));
		form.add(new TextField("websiteInput", new PropertyModel(this, "editedUser.address.website")));
		form.add(new TextField("streetInput", new PropertyModel(this, "editedUser.address.street")));
		form.add(new TextField("houseNumberInput", new PropertyModel(this, "editedUser.address.houseNumber")));
		form.add(new TextField("cityInput", new PropertyModel(this, "editedUser.address.city")));
		form.add(new TextField("zipCodeInput", new PropertyModel(this, "editedUser.address.zipCode")));
		form.add(new TextField("countryInput", new PropertyModel(this, "editedUser.address.country")));

		form.add(new Button("acceptButton") {

			private static final long serialVersionUID = -4742823374395732L;

			@Override
			public void onSubmit() {
				//TODO check if username exists
//				if(password.equals(rePassword)) {
//					idoncService.registerUser(user, password);
//				}
				setResponsePage(UserListPage.class);
			}
		});

		form.add(new Button("cancelButton") {

			private static final long serialVersionUID = 6703091768251015771L;

			@Override
			public void onSubmit() {
				setResponsePage(UserListPage.class);
			}
		});

        Link accept = new Link("acceptLink") {
            @Override
            public void onClick() {
                setResponsePage(UserListPage.class);
            }
        };

        Link cancel = new Link("cancelLink") {

            @Override
            public void onClick() {
                setResponsePage(UserListPage.class);
            }
        };
        add(accept);
        add(cancel);

		add(form);
	}
}
