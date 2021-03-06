package org.lopatka.idonc.web.page.project;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.dataproviders.ProjectDataProvider;
import org.lopatka.idonc.service.IdoncService;

@AuthorizeInstantiation(value = {Roles.ADMIN, Roles.USER})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.USER, Roles.ADMIN})
public class ProjectsListPage extends BasePage {

   private static final long serialVersionUID = 1L;

   @SpringBean(name = "idoncService")
	private IdoncService idoncService;

	private IdoncSession session = IdoncSession.get();

	private IdoncUser currentUser = session.getLoggedUser().getUser();

	public ProjectsListPage() {
		initLayout();
	}

	private void initLayout() {
		ProjectDataProvider prov = new ProjectDataProvider(idoncService);
		DataView table = new DataView("pageable", prov) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item item) {
				IdoncProject project = (IdoncProject) item.getModelObject();
				item.add(new Label("name", project.getName()));
				PageParameters params = new PageParameters();
				params.add("projectname", project.getName());
				item.add(new BookmarkablePageLink("details", ProjectDetailsPage.class, params));

				BookmarkablePageLink activateLink = new BookmarkablePageLink("setActiveProject", SetActiveProjectPage.class, params);
				activateLink.setVisibilityAllowed(true);
				activateLink.setOutputMarkupPlaceholderTag(true);
				item.add(activateLink);

				if(currentUser.getContributedProject()!= null && (currentUser.getContributedProject().getId().equals(project.getId()))) {
					activateLink.setVisible(false);
				}

				session.setProject(project.getName(), project);

			}

		};
		table.setItemsPerPage(10);
		add(table);
		add(new PagingNavigator("navigator", table));
	}
}
