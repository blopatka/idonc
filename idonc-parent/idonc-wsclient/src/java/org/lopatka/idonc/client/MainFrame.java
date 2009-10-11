package org.lopatka.idonc.client;

import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		ResourceMap resourceMap = Application.getInstance(MainIdoncApp.class).getContext().getResourceMap(MainFrame.class);
		
		
	}

}
