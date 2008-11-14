package org.lopatka.idonc.web.page.project;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.web.IdoncSession;
import org.lopatka.idonc.web.model.data.IdoncProject;
import org.lopatka.idonc.web.page.component.BasePage;

@AuthorizeInstantiation("ADMIN")
public class ProjectDetailsPage extends BasePage {

	public ProjectDetailsPage(PageParameters params) {
		super(params);
		this.setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
			
			private static final long serialVersionUID = -3624462692938027960L;

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
