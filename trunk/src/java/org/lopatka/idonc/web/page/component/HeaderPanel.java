package org.lopatka.idonc.web.page.component;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.lopatka.idonc.web.page.HomePage;
import org.lopatka.idonc.web.page.LogoutPage;
import org.lopatka.idonc.web.page.component.suckerfish.SuckerfishMenuPanel;
import org.lopatka.idonc.web.page.info.AboutPage;
import org.lopatka.idonc.web.page.info.FaqPage;
import org.lopatka.idonc.web.page.user.EditUserPage;
import org.lopatka.idonc.web.page.user.UserListPage;

/**
 * @author Bartek
 */
public class HeaderPanel extends Panel {

	private static final long serialVersionUID = -7253076246501732670L;

	public HeaderPanel(String id) {
		super(id);
		final SuckerfishMenuPanel menuBar = new SuckerfishMenuPanel("menuBar");
		add(menuBar);

		final SuckerfishMenuPanel.MenuItem home = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, HomePage.class), "Home");
		
		final SuckerfishMenuPanel.MenuItem users = new SuckerfishMenuPanel.MenuItem("Users");
		final SuckerfishMenuPanel.MenuItem list = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, UserListPage.class),"List");
		final SuckerfishMenuPanel.MenuItem edit = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, EditUserPage.class), "Edit");

		final SuckerfishMenuPanel.MenuItem info = new SuckerfishMenuPanel.MenuItem("Info");
		final SuckerfishMenuPanel.MenuItem faq = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, FaqPage.class), "FAQ");
		final SuckerfishMenuPanel.MenuItem about = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID, AboutPage.class), "About");

		final SuckerfishMenuPanel.MenuItem logout = new SuckerfishMenuPanel.MenuItem(
				new PageLink(SuckerfishMenuPanel.LINK_ID, LogoutPage.class), "Logout");
		
		
		menuBar.addMenu(home);
		
		users.addMenu(list);
		users.addMenu(edit);
		menuBar.addMenu(users);
		
		info.addMenu(faq);
		info.addMenu(about);
		menuBar.addMenu(info);
	
		menuBar.addMenu(logout);
	}

}

