package org.lopatka.idonc.client;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lopatka.idonc.model.user.LoggedUser;

public class AppSession {

	final static String MAIN_PANEL = "Main Panel";
	final static String LOGIN_PANEL = "Login Panel";
	final static String CALCULATION_PANEL = "Calculation Panel";

	private JPanel mainPanel;
	private JPanel loginPanel;
	private JPanel calculationPanel;

	private JFrame mainFrame;

	private LoggedUser loggedUser;

	private AppSession() {}

	private static class AppSessionHolder {
		private static final AppSession INSTANCE  = new AppSession();
	}

	public static AppSession getInstance() {
		return AppSessionHolder.INSTANCE;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JPanel getLoginPanel() {
		return loginPanel;
	}

	public void setLoginPanel(JPanel loginPanel) {
		this.loginPanel = loginPanel;
	}

	public JPanel getCalculationPanel() {
		return calculationPanel;
	}

	public void setCalculationPanel(JPanel calculationPanel) {
		this.calculationPanel = calculationPanel;
	}

}
