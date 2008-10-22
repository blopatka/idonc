package org.lopatka.idonc.web.page.component;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class OneColumnContentPanel extends Panel {

	private static final long serialVersionUID = -8599340044919336995L;
	
	public OneColumnContentPanel(String id) {
		super(id);
		add(getMainColumn("mainColumn"));
	}

	public abstract Component getMainColumn(String componentId);

}
