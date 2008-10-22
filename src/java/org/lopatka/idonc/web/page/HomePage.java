package org.lopatka.idonc.web.page;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.component.ThreeColumnContentPanel;

public class HomePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {

	}

	@Override
	public Panel getContentPanel(String id) {
		return new ThreeColumnContentPanel(id) {

			@Override
			public Component getLeftColumn(String columnId) {
				Fragment f = new Fragment(columnId,"leftColumnFragment", HomePage.this);
				return f;
			}

			@Override
			public Component getMainColumn(String columnId) {
				Fragment f = new Fragment(columnId, "mainColumnFragment", HomePage.this);

				f.add(new Label("message","If you see this message WICKED is properly configured and running"));

				return f;
			}

			@Override
			public Component getRightColumn(String columnId) {
				Fragment f = new Fragment(columnId, "rightColumnFragment", HomePage.this);
				return f;
			}

		};
	}
}
