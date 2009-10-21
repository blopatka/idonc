package org.lopatka.idonc.client;

import info.clearthought.layout.TableLayout;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.lopatka.idonc.model.user.LoggedUser;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private AppSession session = AppSession.getInstance();

	private JLabel usernameLabel;
	private JTextField usernameField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton cancelButton;

	public LoginPanel() {
		setBounds(0, 0, 400, 400);
		double size[][] = {{10, 100, 10, 100, 100, TableLayout.FILL}, {10, 20, 10, 20, 10, 20, TableLayout.FILL}};
		setLayout(new TableLayout(size));
		initComponents();
	}

	private void initComponents() {
		usernameLabel = new JLabel();
		usernameField = new JTextField();
		passwordLabel = new JLabel();
		passwordField = new JPasswordField();
		loginButton = new JButton();
		cancelButton = new JButton();

		ResourceMap resourceMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getResourceMap(LoginPanel.class);

		ActionMap actionMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getActionMap(MainFrame.class, session.getMainFrame());


		ActionMap actions = Application.getInstance(MainIdoncApp.class).getContext().getActionMap(LoginPanel.class, this);

		usernameLabel.setName(resourceMap.getString("username.label.name"));
		usernameLabel.setText(resourceMap.getString("username.label.text"));
		add(usernameLabel, "1, 1");

		usernameField.setName(resourceMap.getString("username.field.name"));
		add(usernameField, "3, 1, 4, 1");

		passwordLabel.setName(resourceMap.getString("password.label.name"));
		passwordLabel.setText(resourceMap.getString("password.label.text"));
		add(passwordLabel, "1, 3");

		passwordField.setName(resourceMap.getString("password.field.name"));
		add(passwordField, "3, 3, 4, 3");

		loginButton.setAction(actions.get("loginUser"));
		loginButton.setName(resourceMap.getString("login.button.name"));
		loginButton.setText(resourceMap.getString("login.button.text"));
		add(loginButton, "1, 5");

		cancelButton.setAction(actions.get("cancelLogin"));
		cancelButton.setName(resourceMap.getString("cancel.button.name"));
		cancelButton.setText(resourceMap.getString("cancel.button.text"));
		add(cancelButton, "3, 5");
	}

	@Action
	public void loginUser() {
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		if((username != null) && (password != null)) {
			LoggedUser lU = AppSession.idoncService.loginUser(username, password);
			if(lU != null) {
				session.setLoggedUser(lU);
				//zalogowano użytkownika, można przejść do karty calculations
				session.switchCard(AppSession.CALCULATION_PANEL);
			}
		}
	}

	@Action
	public void cancelLogin() {
		session.switchCard(AppSession.MAIN_PANEL);
	}

}
