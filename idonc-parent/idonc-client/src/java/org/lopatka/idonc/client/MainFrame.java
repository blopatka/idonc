package org.lopatka.idonc.client;

import java.awt.CardLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		ResourceMap resourceMap = Application.getInstance(MainIdoncApp.class)
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

		actionsMenu.add(beginWorkMenuItem);

		stopWorkMenuItem.setAction(actionMap.get("stopWork"));
		stopWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		stopWorkMenuItem.setText(resourceMap.getString("StopWork.text"));
		stopWorkMenuItem.setName(resourceMap.getString("StopWork.name"));
		stopWorkMenuItem.setIcon(resourceMap.getIcon("StopWork.icon"));
		actionsMenu.add(stopWorkMenuItem);

		mainMenuBar.add(actionsMenu);

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

//		GroupLayout layout = new GroupLayout(getContentPane());
//		getContentPane().setLayout(layout);
//
//		layout.setHorizontalGroup(layout.createParallelGroup(
//				GroupLayout.Alignment.LEADING).addComponent(cards,
//				GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE));
//		layout.setVerticalGroup(layout.createParallelGroup(
//				GroupLayout.Alignment.LEADING).addComponent(cards,
//				GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE));

		pack();



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
//		AppSession.idoncService.logoutUser(session.getLoggedUser().getUser().getUserName(), session.getLoggedUser().getSessionId());
	}

	@Action
	public void beginWork() {
		((CalculationPanel)session.getCalculationPanel()).loadPart();
		System.out.println("begin work");
	}

	@Action
	public void stopWork() {
		session.setCalculationInterrupted(true);
		System.out.println("stop work");
	}

}
