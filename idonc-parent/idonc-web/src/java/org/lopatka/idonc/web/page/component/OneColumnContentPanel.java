package org.lopatka.idonc.web.page.component;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class OneColumnContentPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	public OneColumnContentPanel(String id) {
		super(id);
		add(getMainColumn("mainColumn"));
	}

	public abstract Component getMainColumn(String componentId);

}
