package org.lopatka.idonc.web.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.IdoncUser;
import org.lopatka.idonc.web.page.component.BasePage;
import org.lopatka.idonc.web.page.component.OneColumnContentPanel;
import org.lopatka.idonc.web.page.component.ThreeColumnContentPanel;
import org.lopatka.idonc.web.page.dataproviders.IdoncUserDataProvider;
import org.lopatka.idonc.web.page.dataproviders.UserDataProvider;

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
					
//				IdoncUserDataProvider prov = new IdoncUserDataProvider(dao);
//				
//				IColumn[] columns = createColumns();
//				DefaultDataTable<IdoncUser> users;
//				users = new DefaultDataTable("users", Arrays.asList(columns), prov, 10);
//				f.add(users);
				
				UserDataProvider prov = new UserDataProvider(dao);
				DataView table = new DataView("pageable", prov) {
					@Override
					protected void populateItem(final Item item) {
						IdoncUser user = (IdoncUser) item.getModelObject();
						item.add(new Label("username", user.getUserName()));
						item.add(new Label("firstname", user.getFirstName()));
						item.add(new Label("lastname", user.getLastName()));
						
//						item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel() {
//							@Override
//							public Object getObject() {
//								return (item.getIndex() % 2 == 1) ? "even" : "odd";
//							}
//						}));
					}
					
				};
				
				table.setItemsPerPage(10);
				f.add(table);
				f.add(new PagingNavigator("navigator", table));
				
				
				Contact contact = null;
		        ArrayList list = new ArrayList();

		        char character;
		        
		        // a - z
		        for (int i = 97; i < 123; i++) {
		            character = (char) i;
		            contact = new Contact(String.valueOf(character));
		            list.add(contact);
		        }
		        
		        final DataView dataView = new DataView("simple", new ListDataProvider(
		                list)) {
		            public void populateItem(final Item item) {
		                final Contact user = (Contact) item.getModelObject();
		                item.add(new Label("id", user.getId()));
		            }
		        };

		         dataView.setItemsPerPage(10);
		        
		        f.add(dataView);

		        f.add(new PagingNavigator("navigator1", dataView));
				
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

	class Contact implements Serializable {

	    private final String id;

	    public Contact(String id) {
	        this.id = id;
	    }

	    public String getId() {
	        return id;
	    }

	}
}

