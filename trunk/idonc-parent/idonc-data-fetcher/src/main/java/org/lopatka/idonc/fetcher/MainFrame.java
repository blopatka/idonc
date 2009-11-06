package org.lopatka.idonc.fetcher;

import info.clearthought.layout.TableLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JLabel projectLabel;
	private JComboBox projectCombo;

	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		ResourceMap resourceMap = Application.getInstance(DataFetcher.class)
		.getContext().getResourceMap(MainFrame.class);

		double [][]size = {{10, 100, 10, 180, 10, 30, TableLayout.FILL},
				{10,20,10,20,10,20,10}};

		mainPanel = new JPanel(new TableLayout(size));
		mainPanel.setBounds(0, 0, 400, 400);
		add(mainPanel);

		projectLabel = new JLabel();
		projectLabel.setName(resourceMap.getString(""));
		mainPanel.add(projectLabel, "1, 1");

		projectCombo = new JComboBox();
		mainPanel.add(projectCombo, "3, 1, 5, 1");




		pack();
	}
}
