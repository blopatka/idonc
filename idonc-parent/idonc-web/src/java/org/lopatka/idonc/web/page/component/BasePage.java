package org.lopatka.idonc.web.page.component;

import java.io.Serializable;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

/**
 * @author Bartek
 */
public abstract class BasePage extends WebPage implements Serializable {
//public abstract class BasePage extends WebPage {

	public BasePage() {
		init();
	}
	
	public BasePage(PageParameters parameters) {
		super(parameters);
		init();
	}
	
	public BasePage(IModel model) {
		super(model);
		init();
	}
	
	private void init() {
		add(new HeaderPanel("header"));
		add(new FooterPanel("footer"));
	}

}

