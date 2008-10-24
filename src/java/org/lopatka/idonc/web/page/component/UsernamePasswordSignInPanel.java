package org.lopatka.idonc.web.page.component;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

public abstract class UsernamePasswordSignInPanel extends Panel {

	private static final long serialVersionUID = -5480384562375357007L;

	public UsernamePasswordSignInPanel(String id) {
		super(id);
		add(new SignInForm("signInForm").setOutputMarkupId(false));
	}

	public abstract boolean signIn(String username, String password);
	
	public final class SignInForm extends StatelessForm {

		private static final long serialVersionUID = 400426386165587956L;

		public SignInForm(final String id) {
			super(id, new CompoundPropertyModel(new ValueMap()));
			
			add(new TextField("username").setOutputMarkupId(false));
			add(new PasswordTextField("password").setOutputMarkupId(false));
		}
		
		@Override
		protected void onSubmit() {
			ValueMap values = (ValueMap) getModelObject();
			String username = values.getString("username");
			String password = values.getString("password");
			
			if(signIn(username, password)) {
				if(!getPage().continueToOriginalDestination()) {
					setResponsePage(Application.get().getHomePage());
				}
			} else {
				error(getLocalizer().getString("exception.login", this,"Illegal username password combo"));
			}
		}
		
	}
}
