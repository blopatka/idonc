package org.lopatka.idonc.client;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.JPopupMenu.Separator;
import javax.swing.plaf.SeparatorUI;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ActionMap mainPaneActionsMap;

	private MainPanel mainPanel;

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
		mainPanel = new MainPanel();
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
		mainPanel.setName(resourceMap.getString("MainPanel.name"));
		mainMenuBar.setName(resourceMap.getString("MenuBar.name"));
		fileMenu.setText(resourceMap.getString("FileMenu.text"));
		fileMenu.setName(resourceMap.getString("FileMenu.name"));

//		mainPaneActionsMap = Application.getInstance(MainIdoncApp.class)
//				.getContext().getActionMap(MainPanel.class, mainPanel);
//		loginMenuItem.setAction(mainPaneActionsMap.get("loginUser"));
		
		ActionMap actionMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getActionMap(MainFrame.class, this);
		
		loginMenuItem.setAction(actionMap.get("loginUser"));
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

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(mainPanel,
				GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(mainPanel,
				GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE));

		pack();

	}
	
	@Action
	public void loginUser() {
		System.out.println("login user");
	}
	
	@Action
	public void quit() {
		Application.getInstance().exit();
	}
	
	@Action
	public void beginWork() {
		System.out.println("begin work");
	}
	
	@Action
	public void stopWork() {
		System.out.println("stop work");
	}

}
