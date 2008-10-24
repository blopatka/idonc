package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.strategies.LoginException;
import org.lopatka.idonc.web.page.component.UsernamePasswordSignInPanel;
import org.lopatka.idonc.web.utils.IdoncLoginContext;

/**
 * @author Bartek
 */
public class LoginPage extends WebPage {

	String username;
	String password;

	public LoginPage() {
		setStatelessHint(true);
		add(new FeedbackPanel("feedback") {
			private static final long serialVersionUID = -1219315660925043617L;
			@Override
			public boolean isVisible() {
				return anyMessage();
			}
		});
		String panelId = "signInPanel";
		newUserPasswordSignInPanel(panelId);
		
//		Form form = new Form("loginForm");
//
//		form.add(new TextField("usernameInput",new PropertyModel(this, "username")));
//		form.add(new PasswordTextField("passwordInput", new PropertyModel(this, "password")));
//		Button loginButton = new Button("loginButton") {
//			@Override
//			public void onSubmit() {
//				//TODO login user with service and then go to homepage (logged)
//				setResponsePage(HomePage.class);
//			}
//		};
//		form.add(loginButton);
//		form.add(new PageLink("registerLink", RegisterUserPage.class));
//
//		add(form);
	}
	
	protected void newUserPasswordSignInPanel(String panelId) {
		add(new UsernamePasswordSignInPanel(panelId) {

			private static final long serialVersionUID = 7568114694962872226L;

			@Override
			public boolean signIn(String username, String password) {
				IdoncLoginContext ctx = new IdoncLoginContext(username, password);
				try {
					((WaspSession)getSession()).login(ctx);
				} catch (LoginException e) {
					return false;
				}
				return true;
			}
			
		});
	}
}

