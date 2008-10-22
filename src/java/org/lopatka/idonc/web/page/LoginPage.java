package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.model.PropertyModel;

/**
 * @author Bartek
 */
public class LoginPage extends WebPage {

	String username;
	String password;

	public LoginPage() {
		Form form = new Form("loginForm");

		form.add(new TextField("usernameInput",new PropertyModel(this, "username")));
		form.add(new PasswordTextField("passwordInput", new PropertyModel(this, "password")));
		Button loginButton = new Button("loginButton") {
			@Override
			public void onSubmit() {
				//TODO login user with service and then go to homepage (logged)
				setResponsePage(HomePage.class);
			}
		};
		form.add(loginButton);
		form.add(new PageLink("registerLink", RegisterUserPage.class));

		add(form);
	}
}

