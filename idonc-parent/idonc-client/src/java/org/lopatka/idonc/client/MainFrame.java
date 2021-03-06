package org.lopatka.idonc.client;

import java.awt.CardLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private AppSession session = AppSession.getInstance();

	private JPanel cards;

	private JMenuBar mainMenuBar;
	private JMenu fileMenu;
	private JMenuItem loginMenuItem;
	private JMenuItem closeApplicationMenuItem;
	private JMenu actionsMenu;
	private JMenuItem beginWorkMenuItem;
	private JMenuItem stopWorkMenuItem;
	private JMenuItem interruptWorkMenuItem;
	private JMenu helpMenu;
	private JMenuItem aboutMenuItem;

	private ResourceMap resourceMap;

	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		session.setMainFrame(this);

		mainMenuBar = new JMenuBar();
		fileMenu = new JMenu();
		loginMenuItem = new JMenuItem();
		closeApplicationMenuItem = new JMenuItem();
		actionsMenu = new JMenu();
		beginWorkMenuItem = new JMenuItem();
		stopWorkMenuItem = new JMenuItem();
		interruptWorkMenuItem = new JMenuItem();
		helpMenu = new JMenu();
		aboutMenuItem = new JMenuItem();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		resourceMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getResourceMap(MainFrame.class);

		setTitle(resourceMap.getString("Form.title"));
		setName(resourceMap.getString("Form.name"));
		mainMenuBar.setName(resourceMap.getString("MenuBar.name"));
		fileMenu.setText(resourceMap.getString("FileMenu.text"));
		fileMenu.setName(resourceMap.getString("FileMenu.name"));



		ActionMap actionMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getActionMap(MainFrame.class, this);

		loginMenuItem.setAction(actionMap.get("showLoginScreen"));
		loginMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				InputEvent.CTRL_MASK));
		loginMenuItem.setText(resourceMap.getString("LoginMenu.text"));
		loginMenuItem.setName(resourceMap.getString("LoginMenu.name"));
		loginMenuItem.setIcon(resourceMap.getIcon("LoginMenu.icon"));
		fileMenu.add(loginMenuItem);


		closeApplicationMenuItem.setAction(actionMap.get("quit"));
		closeApplicationMenuItem.setText(resourceMap.getString("CloseMenu.text"));
		closeApplicationMenuItem.setName(resourceMap.getString("CloseMenu.name"));
		closeApplicationMenuItem.setIcon(resourceMap.getIcon("CloseMenu.icon"));
		fileMenu.add(closeApplicationMenuItem);

		mainMenuBar.add(fileMenu);

		actionsMenu.setText(resourceMap.getString("ActionsMenu.text"));
		actionsMenu.setName(resourceMap.getString("ActionsMenu.name"));

		beginWorkMenuItem.setAction(actionMap.get("beginWork"));
		beginWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		beginWorkMenuItem.setText(resourceMap.getString("BeginWork.text"));
		beginWorkMenuItem.setName(resourceMap.getString("BeginWork.name"));
		beginWorkMenuItem.setIcon(resourceMap.getImageIcon("BeginWork.icon"));
		beginWorkMenuItem.setEnabled(false);
		actionsMenu.add(beginWorkMenuItem);

		stopWorkMenuItem.setAction(actionMap.get("stopWork"));
		stopWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		stopWorkMenuItem.setText(resourceMap.getString("StopWork.text"));
		stopWorkMenuItem.setName(resourceMap.getString("StopWork.name"));
		stopWorkMenuItem.setIcon(resourceMap.getIcon("StopWork.icon"));
		stopWorkMenuItem.setEnabled(false);
		actionsMenu.add(stopWorkMenuItem);

		interruptWorkMenuItem.setAction(actionMap.get("interruptWork"));
		interruptWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		interruptWorkMenuItem.setText(resourceMap.getString("InterruptWork.text"));
		interruptWorkMenuItem.setName(resourceMap.getString("InterruptWork.name"));
		interruptWorkMenuItem.setIcon(resourceMap.getIcon("InterruptWork.icon"));
		interruptWorkMenuItem.setEnabled(false);
		actionsMenu.add(interruptWorkMenuItem);

		mainMenuBar.add(actionsMenu);

		helpMenu.setText(resourceMap.getString("HelpMenu.text"));
		helpMenu.setName(resourceMap.getString("HelpMenu.name"));

		aboutMenuItem.setAction(actionMap.get("showAbout"));
		aboutMenuItem.setText(resourceMap.getString("About.text"));
		aboutMenuItem.setName(resourceMap.getString("About.name"));
		helpMenu.add(aboutMenuItem);

		mainMenuBar.add(helpMenu);

		setJMenuBar(mainMenuBar);

		session.setLoginPanel(new LoginPanel());
		session.setMainPanel(new MainPanel());
		session.setCalculationPanel(new CalculationPanel());

		cards = new JPanel(new CardLayout());
		session.setCardLayout((CardLayout)cards.getLayout(), cards);
		cards.add(session.getMainPanel(), AppSession.MAIN_PANEL);
		cards.add(session.getLoginPanel(), AppSession.LOGIN_PANEL);
		cards.add(session.getCalculationPanel(), AppSession.CALCULATION_PANEL);

		add(cards);
		
		pack();
		


	}

	@Action
	public void showAbout() {
		JOptionPane.showMessageDialog(this, resourceMap.getString("about.content"));
	}

	@Action
	public void showLoginScreen() {
		session.switchCard(AppSession.LOGIN_PANEL);
	}

	@Action
	public void cancelLogin() {
		session.switchCard(AppSession.MAIN_PANEL);
	}

	@Action
	public void quit() {
		Application.getInstance().exit();
	}

	@Action
	public void beginWork() {
		session.getMainFrame().setLoginButtonEnabled(false);
		session.getMainFrame().setBeginWorkButtonEnabled(false);
		session.getMainFrame().setStopWorkButtonEnabled(true);
		session.getMainFrame().setInterruptWorkButtonEnabled(true);
		((CalculationPanel)session.getCalculationPanel()).loadPart();
		System.out.println("begin work");
	}

	@Action
	public void stopWork() {
		session.getMainFrame().setBeginWorkButtonEnabled(false);
		session.getMainFrame().setStopWorkButtonEnabled(false);
		session.getMainFrame().setLoginButtonEnabled(false);
		session.getMainFrame().setInterruptWorkButtonEnabled(false);
		session.setCalculationStopped(true);
		System.out.println("stop work");
	}

	@Action
	public void interruptWork() {
		session.getMainFrame().setLoginButtonEnabled(false);
		session.getMainFrame().setBeginWorkButtonEnabled(true);
		session.getMainFrame().setStopWorkButtonEnabled(false);
		session.getMainFrame().setInterruptWorkButtonEnabled(false);
		session.setCalculationStopped(true);
		session.getThread().cancel(true);
	}

	public void setBeginWorkButtonEnabled(boolean enable) {
		this.beginWorkMenuItem.setEnabled(enable);
	}

	public void setStopWorkButtonEnabled(boolean enable) {
		this.stopWorkMenuItem.setEnabled(enable);
	}

	public void setLoginButtonEnabled(boolean enable) {
		this.loginMenuItem.setEnabled(enable);
	}

	public void setInterruptWorkButtonEnabled(boolean enable) {
		this.interruptWorkMenuItem.setEnabled(enable);
	}
}
