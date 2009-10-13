package org.lopatka.idonc.client;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

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

		mainPaneActionsMap = Application.getInstance(MainIdoncApp.class)
				.getContext().getActionMap(MainPanel.class, mainPanel);
		loginMenuItem.setAction(mainPaneActionsMap.get("loginUser"));
		loginMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0,
				InputEvent.CTRL_MASK));
		loginMenuItem.setText(resourceMap.getString("LoginMenu.text"));
		loginMenuItem.setName(resourceMap.getString("LoginMenu.name"));
		fileMenu.add(loginMenuItem);

		ActionMap actionMap = Application.getInstance(MainIdoncApp.class)
				.getContext().getActionMap(MainFrame.class, this);
		closeApplicationMenuItem.setAction(actionMap.get("quit"));
		closeApplicationMenuItem.setText(resourceMap.getString("CloseMenu.text"));
		closeApplicationMenuItem.setName(resourceMap.getString("CloseMenu.name"));
		fileMenu.add(closeApplicationMenuItem);

		mainMenuBar.add(fileMenu);

		actionsMenu.setText(resourceMap.getString("ActionsMenu.text"));
		actionsMenu.setName(resourceMap.getString("ActionsMenu.name"));

		beginWorkMenuItem.setAction(actionMap.get("beginWork"));
		beginWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		beginWorkMenuItem.setText(resourceMap.getString("BeginWork.text"));
		beginWorkMenuItem.setName(resourceMap.getString("BeginWork.name"));
		actionsMenu.add(beginWorkMenuItem);

		stopWorkMenuItem.setAction(actionMap.get("stopWork"));
		stopWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		stopWorkMenuItem.setText(resourceMap.getString("StopWork.text"));
		stopWorkMenuItem.setName(resourceMap.getString("StopWork.name"));
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

}
