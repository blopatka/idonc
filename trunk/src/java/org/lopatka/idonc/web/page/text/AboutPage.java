package org.lopatka.idonc.web.page.text;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.lopatka.idonc.web.page.component.BasePage;

/**
 * @author Bartek
 */
public class AboutPage extends BasePage {

	public AboutPage() {
		initLayout();
	}

	private void initLayout() {
		add(new Label("aboutTitle", new Model("About")));
	}
}
