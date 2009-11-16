package org.lopatka.idonc.web.page;

import org.apache.wicket.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.PageLink;

/**
 * @author Bartek
 */
public class LoginPage extends SignInPage {

    private static final long serialVersionUID = 1L;

	public LoginPage() {
		setStatelessHint(true);
		Form form = new Form("loginForm");
		form.add(new PageLink("registerLink", RegisterUserPage.class));
		add(form);
	}
}

