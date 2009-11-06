package org.lopatka.idonc.fetcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import info.clearthought.layout.TableLayout;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JLabel projectLabel;
	private JComboBox projectCombo;
	private JLabel dataTypeLabel;
	private JComboBox dataTypeCombo;
	private JLabel fileLabel;
	private JTextField fileNameField;
	private JButton fileChooseButton;
	private File selFile;
	private JButton startButton;

	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		ResourceMap resourceMap = Application.getInstance(DataFetcher.class)
				.getContext().getResourceMap(MainFrame.class);

		ActionMap actionMap = Application.getInstance(DataFetcher.class)
		.getContext().getActionMap(MainFrame.class, this);

		double[][] size = { { 10, 100, 10, 180, 10, 30, 10, TableLayout.FILL },
				{ 10, 20, 10, 20, 10, 20, 10, 20, 10, TableLayout.FILL } };

		setName(resourceMap.getString("frame.name"));
		setTitle(resourceMap.getString("frame.title"));

		mainPanel = new JPanel(new TableLayout(size));
		mainPanel.setBounds(0, 0, 500, 500);
		add(mainPanel);

		projectLabel = new JLabel();
		projectLabel.setName(resourceMap.getString("projectLabel.name"));
		projectLabel.setText(resourceMap.getString("projectLabel.text"));
		mainPanel.add(projectLabel, "1, 1");

		projectCombo = new JComboBox();
		projectCombo.setName(resourceMap.getString("projectCombo.name"));
		mainPanel.add(projectCombo, "3, 1, 5, 1");

		dataTypeLabel = new JLabel();
		dataTypeLabel.setName(resourceMap.getString("dataTypeLabel.name"));
		dataTypeLabel.setText(resourceMap.getString("dataTypeLabel.text"));
		mainPanel.add(dataTypeLabel, "1, 3");

		dataTypeCombo = new JComboBox();
		dataTypeCombo.setName(resourceMap.getString("dataTypeCombo.name"));
		mainPanel.add(dataTypeCombo, "3, 3, 5, 3");

		fileLabel = new JLabel();
		fileLabel.setName(resourceMap.getString("fileLabel.name"));
		fileLabel.setText(resourceMap.getString("fileLabel.text"));
		mainPanel.add(fileLabel, "1, 5");

		fileNameField = new JTextField();
		fileNameField.setName(resourceMap.getString("fileNameField.name"));
		fileNameField.setEditable(false);
		mainPanel.add(fileNameField, "3, 5");

		fileChooseButton = new JButton();
		fileChooseButton.setName(resourceMap.getString("fileChooseButton.name"));
		fileChooseButton.setText(resourceMap.getString("fileChooseButton.text"));
		fileChooseButton.setAction(actionMap.get("chooseFile"));
		mainPanel.add(fileChooseButton, "5, 5");

		startButton = new JButton();
		startButton.setName(resourceMap.getString("startButton.name"));
		startButton.setText(resourceMap.getString("startButton.text"));
		startButton.setEnabled(false);
		mainPanel.add(startButton, "3, 7");

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("fetching data");
			}
		});

		pack();
	}

	@Action
	public void chooseFile() {
		System.out.println("choose file");
		JFileChooser fC = new JFileChooser();
		fC.showSaveDialog(this);
		selFile = fC.getSelectedFile();
		if(selFile != null) {
			fileNameField.setText(selFile.toString());
			fileNameField.setToolTipText(selFile.toString());
			startButton.setEnabled(true);
		} else {
			fileNameField.setText("");
			fileNameField.setToolTipText("");
			startButton.setEnabled(false);
		}
	}

}
