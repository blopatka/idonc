package org.lopatka.idonc.client;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class MainPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private JLabel label;
	public MainPanel() {
		initComponents();
	}

	private void initComponents() {
		ResourceMap resourceMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getResourceMap(MainPanel.class);
		
		label = new JLabel();
		label.setText(resourceMap.getString("mainPanel.label"));
		add(label);
	}
}
