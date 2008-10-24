package org.lopatka.idonc.web.page.component;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * @author Bartek
 */
public class FooterPanel extends Panel {

	private static final long serialVersionUID = 4074581733492005075L;

	public FooterPanel(String id) {
		super(id);
		add(new Label("footerLabel", new Model("text z podpisu")));
	}

}

