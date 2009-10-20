package org.lopatka.idonc.client;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

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

		usernameLabel.setName(resourceMap.getString("username.label.name"));
		usernameLabel.setText(resourceMap.getString("username.label.text"));
		add(usernameLabel);

		usernameField.setName(resourceMap.getString("username.field.name"));
		add(usernameField);

		passwordLabel.setName(resourceMap.getString("password.label.name"));
		passwordLabel.setText(resourceMap.getString("password.label.text"));
		add(passwordLabel);

		passwordField.setName(resourceMap.getString("password.field.name"));
		add(passwordField);

		loginButton.setAction(actionMap.get("showLoginScreen"));
		loginButton.setName(resourceMap.getString("login.button.name"));
		loginButton.setText(resourceMap.getString("login.button.text"));
		add(loginButton);

		cancelButton.setAction(actionMap.get("cancelLogin"));
		cancelButton.setName(resourceMap.getString("cancel.button.name"));
		cancelButton.setText(resourceMap.getString("cancel.button.text"));
		add(cancelButton);
	}

}
