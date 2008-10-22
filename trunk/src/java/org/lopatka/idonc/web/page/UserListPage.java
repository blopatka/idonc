package org.lopatka.idonc.web.page;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.component.OneColumnContentPanel;
import org.lopatka.idonc.web.page.component.ThreeColumnContentPanel;
import org.lopatka.idonc.web.page.dataproviders.IdoncUserDataProvider;

/**
 * @author Bartek
 */
public class UserListPage extends BasePage {

	@SpringBean(name= "userDao")
	private UserDao dao;
	
	public void UserListPage() {
		
	}
	
	

	@Override
	public Panel getContentPanel(String id) {
		return new ThreeColumnContentPanel(id) {
		
			@Override
			public Component getMainColumn(String componentId) {
				Fragment f = new Fragment(componentId, "mainColumnFragment", UserListPage.this);
					
				IdoncUserDataProvider prov = new IdoncUserDataProvider(dao);
				
				IColumn[] columns = createColumns();
				DefaultDataTable<IdoncUser> users;
				users = new DefaultDataTable("users", Arrays.asList(columns), prov, 10);
				f.add(users);
				
				return f;
			}

			@Override
			public Component getLeftColumn(String columnId) {
				Fragment f = new Fragment(columnId, "leftColumnFragment", UserListPage.this);
				return f;
			}

			@Override
			public Component getRightColumn(String columnId) {
				Fragment f = new Fragment(columnId, "rightColumnFragment", UserListPage.this);
				return f;
			}
		};
	}
	
	private IColumn[] createColumns() {
		IColumn[] columns = new IColumn[3];
		columns[0] = new PropertyColumn(new Model("Username"), "userName");
		columns[1] = new PropertyColumn(new Model("First name"), "firstName");
		columns[2] = new PropertyColumn(new Model("Last name"), "lastName");
		return columns;
	}

}

