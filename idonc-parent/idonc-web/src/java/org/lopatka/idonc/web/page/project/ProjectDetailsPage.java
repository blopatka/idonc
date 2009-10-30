package org.lopatka.idonc.web.page.project;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation("ADMIN")
public class ProjectDetailsPage extends BasePage {

    private static final long serialVersionUID = 1L;

	public ProjectDetailsPage(PageParameters params) {
		super(params);
		this.setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Object load() {
				String name = getPageParameters().getString("projectname");
				IdoncSession session = IdoncSession.get();
				return session.getProject(name);
			}
		}));

		initLayout();
	}

	private void initLayout() {
		IdoncProject project = (IdoncProject) ProjectDetailsPage.this.getModelObject();
		Form form = new Form("projectForm");

		form.add(new Label("name", project.getName()));
		form.add(new Label("website", project.getWebsite()));
		form.add(new Label("description", project.getDescription()));
		form.add(new ListView("listView", project.getActiveUsers()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				item.add(new Label("userName", item.getModel()));
			}

		});
		form.add(new Button("backButton") {
			private static final long serialVersionUID = 3795380064734254239L;

			@Override
			public void onSubmit() {
				setResponsePage(ProjectsListPage.class);
			}
		});
		add(form);
	}
}
