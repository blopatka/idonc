package org.lopatka.idonc.client;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private JLabel label;
	public MainPanel() {
		initComponents();
	}

	private void initComponents() {
		label = new JLabel();
		label.setText("witamy w aplikacji IDONC");
		add(label);
	}
}
