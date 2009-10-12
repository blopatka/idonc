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
		// setTitle(resourceMap.getString("Form.title"));
		// setName("");
		// mainPanel.setName("");
		// mainMenuBar.setName("");
		// fileMenu.setText("");
		// fileMenu.setName("");

		mainPaneActionsMap = Application.getInstance(MainIdoncApp.class)
				.getContext().getActionMap(MainPanel.class, mainPanel);
		loginMenuItem.setAction(mainPaneActionsMap.get("loginUser"));
		loginMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0,
				InputEvent.CTRL_MASK));
		// loginMenuItem.setText("");
		// loginMenuItem.setName("");
		fileMenu.add(loginMenuItem);

		ActionMap actionMap = Application.getInstance(MainIdoncApp.class)
				.getContext().getActionMap(MainFrame.class, this);
		closeApplicationMenuItem.setAction(actionMap.get("quit"));
		//closeApplicationMenuItem.setText("");
		//closeApplicationMenuItem.setName("");
		fileMenu.add(closeApplicationMenuItem);

		mainMenuBar.add(fileMenu);

		//actionsMenu.setText("");
		//actionsMenu.setName("");

		beginWorkMenuItem.setAction(actionMap.get("beginWork"));
		beginWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		//beginWorkMenuItem.setText("");
		//beginWorkMenuItem.setName("");
		actionsMenu.add(beginWorkMenuItem);

		stopWorkMenuItem.setAction(actionMap.get("stopWork"));
		beginWorkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		//stopWorkMenuItem.setText("");
		//stopWorkMenuItem.setName("");
		actionsMenu.add(stopWorkMenuItem);

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
