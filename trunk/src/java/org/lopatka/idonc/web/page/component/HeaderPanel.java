package org.lopatka.idonc.web.page.component;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.lopatka.idonc.web.page.HomePage;
import org.lopatka.idonc.web.page.UserListPage;
import org.lopatka.idonc.web.page.component.suckerfish.SuckerfishMenuPanel;
import org.lopatka.idonc.web.page.text.AboutPage;
import org.lopatka.idonc.web.page.text.FaqPage;

/**
 * @author Bartek
 */
public class HeaderPanel extends Panel {

	public HeaderPanel(String id) {
		super(id);
		final SuckerfishMenuPanel menuBar = new SuckerfishMenuPanel("menuBar");
		add(menuBar);

		final SuckerfishMenuPanel.MenuItem home = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, HomePage.class), "Home");
		final SuckerfishMenuPanel.MenuItem users = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, UserListPage.class),"Users");

		final SuckerfishMenuPanel.MenuItem info = new SuckerfishMenuPanel.MenuItem("Info");
		final SuckerfishMenuPanel.MenuItem faq = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, FaqPage.class), "FAQ");
		final SuckerfishMenuPanel.MenuItem about = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, AboutPage.class), "About");

		menuBar.addMenu(home);
		menuBar.addMenu(users);
		menuBar.addMenu(info);

		info.addMenu(faq);
		info.addMenu(about);
	}

}

