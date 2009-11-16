package org.lopatka.idonc.web.page.component;

import java.util.Locale;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.page.HomePage;
import org.lopatka.idonc.web.page.LogoutPage;
import org.lopatka.idonc.web.page.component.suckerfish.SuckerfishMenuPanel;
import org.lopatka.idonc.web.page.info.AboutPage;
import org.lopatka.idonc.web.page.info.FaqPage;
import org.lopatka.idonc.web.page.project.ProjectRunPage;
import org.lopatka.idonc.web.page.project.ProjectsListPage;
import org.lopatka.idonc.web.page.user.EditUserPage;
import org.lopatka.idonc.web.page.user.UserListPage;

/**
 * @author Bartek
 */
public class HeaderPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private Link englishLink;
	private Link polishLink;

	public HeaderPanel(String id) {
		super(id);

		englishLink = new Link("english") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				IdoncSession.get().setLocale(new Locale("en"));
				setResponsePage(HomePage.class);
			}
		};

		polishLink = new Link("polski") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				IdoncSession.get().setLocale(new Locale("pl", "PL"));
				setResponsePage(HomePage.class);
			}
		};

		englishLink.setOutputMarkupPlaceholderTag(true);
		englishLink.setVisibilityAllowed(true);
		polishLink.setOutputMarkupPlaceholderTag(true);
		polishLink.setVisibilityAllowed(true);

		add(englishLink);
		add(polishLink);
		if (IdoncSession.get().getLocale().equals(Locale.ENGLISH)) {
			polishLink.setVisible(true);
			englishLink.setVisible(false);
		} else {
			englishLink.setVisible(true);
			polishLink.setVisible(false);
		}

		final SuckerfishMenuPanel menuBar = new SuckerfishMenuPanel("menuBar");
		add(menuBar);

		final SuckerfishMenuPanel.MenuItem home = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						HomePage.class), new StringResourceModel(
						"headermenu.home", this, null).getString());

		final SuckerfishMenuPanel.MenuItem users = new SuckerfishMenuPanel.MenuItem(
				new StringResourceModel("headermenu.users", this, null)
						.getString());
		final SuckerfishMenuPanel.MenuItem usersList = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						UserListPage.class), new StringResourceModel(
						"headermenu.users.list", this, null).getString());
		final SuckerfishMenuPanel.MenuItem edit = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						EditUserPage.class), new StringResourceModel(
						"headermenu.users.edit", this, null).getString());

		final SuckerfishMenuPanel.MenuItem projects = new SuckerfishMenuPanel.MenuItem(
				new StringResourceModel("headermenu.projects", this, null)
						.getString());
		final SuckerfishMenuPanel.MenuItem projectsList = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						ProjectsListPage.class), new StringResourceModel(
						"headermenu.projects.list", this, null).getString());
		final SuckerfishMenuPanel.MenuItem runProject = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						ProjectRunPage.class), new StringResourceModel(
						"headermenu.projects.run", this, null).getString());

		final SuckerfishMenuPanel.MenuItem info = new SuckerfishMenuPanel.MenuItem(
				new StringResourceModel("headermenu.info", this, null)
						.getString());
		final SuckerfishMenuPanel.MenuItem faq = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						FaqPage.class), new StringResourceModel(
						"headermenu.info.faq", this, null).getString());
		final SuckerfishMenuPanel.MenuItem about = new SuckerfishMenuPanel.MenuItem(
				new BookmarkablePageLink(SuckerfishMenuPanel.LINK_ID,
						AboutPage.class), new StringResourceModel(
						"headermenu.projects.about", this, null).getString());

		final SuckerfishMenuPanel.MenuItem logout = new SuckerfishMenuPanel.MenuItem(
				new PageLink(SuckerfishMenuPanel.LINK_ID, LogoutPage.class),
				new StringResourceModel("headermenu.logout", this, null)
						.getString());

		menuBar.addMenu(home);

		users.addMenu(usersList);
		users.addMenu(edit);
		menuBar.addMenu(users);

		projects.addMenu(projectsList);
		projects.addMenu(runProject);
		menuBar.addMenu(projects);

		info.addMenu(faq);
		info.addMenu(about);
		menuBar.addMenu(info);

		menuBar.addMenu(logout);
	}

}
