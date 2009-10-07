package org.lopatka.idonc.web.page;

import org.apache.wicket.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.PageLink;
import org.lopatka.idonc.Celsius;
import org.lopatka.idonc.Farenheit;
import org.lopatka.idonc.service.ConverterService;
import org.lopatka.idonc.web.IdoncSession;

/**
 * @author Bartek
 */
public class LoginPage extends SignInPage {

    private static final long serialVersionUID = 1L;

	public LoginPage() {
		setStatelessHint(true);
		Form form = new Form("loginForm");
		form.add(new PageLink("registerLink", RegisterUserPage.class));

		ConverterService service = IdoncSession.get().getConverterService();
		Celsius cel = new Celsius();
		cel.setTemperatur(100);
		Farenheit far = (Farenheit) service.celsiusToFarenheit(cel);
		String value = "100 celsius in farenheit is: "+far.getTemperature();
		form.add(new Label("conversion",value));

		add(form);
	}
}

