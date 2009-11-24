package org.lopatka.idonc.web.page.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncApplication;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.dataproviders.ProjectDataProvider;
import org.lopatka.idonc.web.page.dataproviders.UserWithoutAdminDataProvider;
import org.lopatka.idonc.web.page.user.UserDetailsPage;

@AuthorizeInstantiation(value = {Roles.ADMIN})
@AuthorizeAction(action = Action.RENDER, roles = {Roles.ADMIN})
public class AdminPage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean(name = "idoncService")
	private IdoncService idoncService;

	private IdoncSession session = IdoncSession.get();

	public AdminPage() {
		initLayout();
	}

	private void initLayout() {

		UserWithoutAdminDataProvider prov = new UserWithoutAdminDataProvider(idoncService);
		DataView table = new DataView("pageable", prov) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item item) {
				IdoncUser user = (IdoncUser) item.getModelObject();
				item.add(new Label("username", user.getUserName()));
				PageParameters param = new PageParameters();
				param.add("username", user.getUserName());
				item.add(new BookmarkablePageLink("details", UserDetailsPage.class, param));
				item.add(new BookmarkablePageLink("delete", UserDeletePage.class, param));
				//persisting in session
				session.setUser(user.getUserName(), user);
			}
		};

		table.setItemsPerPage(10);
		add(table);
		add(new PagingNavigator("navigator", table));

		ProjectDataProvider prov2 = new ProjectDataProvider(idoncService);
		DataView table2 = new DataView("pageable2", prov2) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item item) {
				IdoncProject project = (IdoncProject) item.getModelObject();
				item.add(new Label("name", project.getName()));

				ResourceLink inputDataLink = new ResourceLink("input", new InputDataResource(project.getId()));
				item.add(inputDataLink);

				ResourceLink outputDataLink = new ResourceLink("output", new OutputDataResource(project.getId()));
				item.add(outputDataLink);
			}

		};
		table2.setItemsPerPage(2);
		add(table2);
		add(new PagingNavigator("navigator2", table2));
	}


	private class InputDataResource extends WebResource {

		private static final long serialVersionUID = 1L;
		private Long id;

		public InputDataResource(Long projectId) {
			this.id = projectId;
		}

		@Override
		public IResourceStream getResourceStream() {
			String input = idoncService.getInputDataForProject(id, session.getLoggedUserName(), session.getSessionId());
			return new StringResourceStream(input, "application/xml");
		}

		@Override
		protected void setHeaders(WebResponse response) {
			super.setHeaders(response);
			String name = IdoncApplication.get().getResourceSettings().getLocalizer().getString("admin.inputLink.name",
			        AdminPage.this);
			SimpleDateFormat dF = new SimpleDateFormat("yyMMddHHmmss");
			name = name + "_" + id + "_" +dF.format(new Date()) + ".xml";
			response.setAttachmentHeader(name);
		}

	}

	private class OutputDataResource extends WebResource {

		private static final long serialVersionUID = 1L;
		private Long id;

		public OutputDataResource(Long projectId) {
			this.id = projectId;
		}

		@Override
		public IResourceStream getResourceStream() {
			String input = idoncService.getOutputDataForProject(id, session.getLoggedUserName(), session.getSessionId());
			return new StringResourceStream(input, "application/xml");
		}

		@Override
		protected void setHeaders(WebResponse response) {
			super.setHeaders(response);
			String name = IdoncApplication.get().getResourceSettings().getLocalizer().getString("admin.outputLink.name",
			        AdminPage.this);
			SimpleDateFormat dF = new SimpleDateFormat("yyMMddHHmmss");
			name = name + "_" + id + "_" +dF.format(new Date()) + ".xml";
			response.setAttachmentHeader(name);
		}

	}
}
