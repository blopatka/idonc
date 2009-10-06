package org.lopatka.idonc.web.page.component;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class TwoColumnContentPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public TwoColumnContentPanel(String id) {
		super(id);
		add(getLeftColumn("leftColumn"));
		add(getMainColumn("mainColumn"));
	}

	public abstract Component getLeftColumn(String componentId);
	public abstract Component getMainColumn(String componentId);
}
