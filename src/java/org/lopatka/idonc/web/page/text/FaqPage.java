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
public class FaqPage extends BasePage {

	@Override
	public Panel getContentPanel(String id) {
		return new ThreeColumnContentPanel(id) {

			@Override
			public Component getLeftColumn(String columnId) {
				Fragment f = new Fragment(columnId, "leftColumnFragment", FaqPage.this);
				return f;
			}

			@Override
			public Component getMainColumn(String columnId) {
				Fragment f = new Fragment(columnId, "mainColumnFragment", FaqPage.this);
				f.add(new Label("faqTitle"));
				return f;
			}

			@Override
			public Component getRightColumn(String columnId) {
				Fragment f = new Fragment(columnId, "rightColumnFragment", FaqPage.this);

				return f;
			}

		};
	}

}

