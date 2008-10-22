package org.lopatka.idonc.web.page.text;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.component.ThreeColumnContentPanel;

/**
 * @author Bartek
 */
public class AboutPage extends BasePage {

	@Override
	public Panel getContentPanel(String id) {
		return new ThreeColumnContentPanel(id) {

			@Override
			public Component getLeftColumn(String columnId) {
				Fragment f = new Fragment(columnId, "leftColumnFragment", AboutPage.this);
				return f;
			}

			@Override
			public Component getMainColumn(String columnId) {
				Fragment f = new Fragment(columnId, "mainColumnFragment", AboutPage.this);

				f.add(new Label("aboutTitle"));

				return f;
			}

			@Override
			public Component getRightColumn(String columnId) {
				Fragment f = new Fragment(columnId, "rightColumnFragment", AboutPage.this);
				return f;
			}

		};
	}

}
