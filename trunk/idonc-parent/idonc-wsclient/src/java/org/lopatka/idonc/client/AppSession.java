package org.lopatka.idonc.client;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.LoggedUser;
import org.lopatka.idonc.service.IdoncService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppSession {

	final static ApplicationContext springContext;
	final static IdoncService idoncService;

	final static String MAIN_PANEL;
	final static String LOGIN_PANEL;
	final static String CALCULATION_PANEL;

	private JPanel mainPanel;
	private JPanel loginPanel;
	private JPanel calculationPanel;

	private JFrame mainFrame;

	private LoggedUser loggedUser;
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private IdoncProject project;

	static {
		springContext = new ClassPathXmlApplicationContext("client-applicationContext.xml");
		idoncService = (IdoncService) springContext.getBean("idoncService");
		MAIN_PANEL = "Main Panel";
		LOGIN_PANEL = "Login Panel";
		CALCULATION_PANEL = "Calculation Panel";
	}

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

	public void setCardLayout(CardLayout layout, JPanel cardPanel) {
		this.cardLayout = layout;
		this.cardPanel = cardPanel;
	}

	public void switchCard(String newCard) {
		cardLayout.show(cardPanel, newCard);
	}

	public void setProject(IdoncProject proj) {
		this.project = proj;
	}

	public IdoncProject getProject() {
		return project;
	}
	
}
