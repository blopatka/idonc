package org.lopatka.idonc.web.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * @author Bartek
 */
public class AuthenticatedPage extends WebPage {

	public AuthenticatedPage() {
		super();
		add(new Label("label", new Model("label label")));
	}
}

