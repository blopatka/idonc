package org.lopatka.idonc.fetcher;

import info.clearthought.layout.TableLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
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
import org.lopatka.idonc.model.data.IdoncLongData;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.PartType;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<IdoncProject> projects;
	private EntityManager em = ApplicationSession.getInstance().getEm();

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
		projects = getProjects();

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
		projectCombo.setModel(new ProjectComboBoxModel(projects));

		dataTypeLabel = new JLabel();
		dataTypeLabel.setName(resourceMap.getString("dataTypeLabel.name"));
		dataTypeLabel.setText(resourceMap.getString("dataTypeLabel.text"));
		mainPanel.add(dataTypeLabel, "1, 3");

		dataTypeCombo = new JComboBox();
		dataTypeCombo.setName(resourceMap.getString("dataTypeCombo.name"));
		mainPanel.add(dataTypeCombo, "3, 3, 5, 3");

		List<DataType> types = new ArrayList<DataType>();
		types.add(new DataType(0, resourceMap.getString("dataType.input")));
		types.add(new DataType(1, resourceMap.getString("dataType.result")));
		dataTypeCombo.setModel(new DataTypeComboBoxModel(types));

		fileLabel = new JLabel();
		fileLabel.setName(resourceMap.getString("fileLabel.name"));
		fileLabel.setText(resourceMap.getString("fileLabel.text"));
		mainPanel.add(fileLabel, "1, 5");

		fileNameField = new JTextField();
		fileNameField.setName(resourceMap.getString("fileNameField.name"));
		fileNameField.setEditable(false);
		mainPanel.add(fileNameField, "3, 5");

		fileChooseButton = new JButton();
		fileChooseButton
				.setName(resourceMap.getString("fileChooseButton.name"));
		fileChooseButton
				.setText(resourceMap.getString("fileChooseButton.text"));
		fileChooseButton.setAction(actionMap.get("chooseFile"));
		mainPanel.add(fileChooseButton, "5, 5");

		startButton = new JButton();
		startButton.setName(resourceMap.getString("startButton.name"));
		startButton.setText(resourceMap.getString("startButton.text"));
		startButton.setEnabled(false);
		mainPanel.add(startButton, "3, 7");

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (projectCombo.getSelectedItem() != null
						&& dataTypeCombo.getSelectedItem() != null) {
					IdoncProject proj = (IdoncProject) projectCombo
							.getSelectedItem();
					System.out.println("pobieranie");
					int selItem = ((DataType) dataTypeCombo.getSelectedItem())
							.getVal();
					switch (selItem) {
					case 0:
						List<IdoncPart> parts = getInputData(proj);
						writeToFile(proj, parts, false);
						fileNameField.setText("");
						fileNameField.setToolTipText("");
						startButton.setEnabled(false);
						break;
					case 1:
						List<IdoncPart> parts1 = getResultData(proj);
						writeToFile(proj, parts1, true);
						fileNameField.setText("");
						fileNameField.setToolTipText("");
						startButton.setEnabled(false);
						break;
					default:
						break;
					}
				}
			}
		});

		pack();
	}

	@SuppressWarnings("unchecked")
	private List<IdoncProject> getProjects() {
		List<IdoncProject> projects = em.createQuery(
				"select distinct p from IdoncProject p").getResultList();
		return projects;
	}

	@Action
	public void chooseFile() {
		System.out.println("choose file");
		JFileChooser fC = new JFileChooser();
		fC.showSaveDialog(this);
		selFile = fC.getSelectedFile();
		if (selFile != null) {
			try {
				fileNameField.setText(selFile.getCanonicalPath());
				fileNameField.setToolTipText(selFile.toString());
				startButton.setEnabled(true);
			} catch (IOException e) {
				e.printStackTrace();
				fileNameField.setText("");
				fileNameField.setToolTipText("");
				startButton.setEnabled(false);
			}
		} else {
			fileNameField.setText("");
			fileNameField.setToolTipText("");
			startButton.setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	private List<IdoncPart> getInputData(IdoncProject project) {
		Query query = em.createQuery("select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType)");
		query.setParameter("id", project.getId());
		query.setParameter("partType", PartType.NEW);
		List<IdoncPart> parts = query.getResultList();
		return parts;
	}

	@SuppressWarnings("unchecked")
	private List<IdoncPart> getResultData(IdoncProject project) {
		Query query = em.createQuery("select distinct parts from org.lopatka.idonc.model.data.IdoncPart parts left join fetch parts.project where (parts.project.id = :id) and (parts.partType = :partType)");
		query.setParameter("id", project.getId());
		query.setParameter("partType", PartType.COMPLETED);
		List<IdoncPart> parts = query.getResultList();
		return parts;
	}

	private void writeToFile(IdoncProject proj, List<IdoncPart> parts,
			boolean isResult) {
		try {
			FileOutputStream fOS = new FileOutputStream(selFile);
			PrintStream stream = new PrintStream(fOS);
			if (proj != null && parts != null) {
				stream.println("<project>");
				stream.println("	<name>" + proj.getName() + "</name>");
				stream.println("	<dataType>" + (isResult == true ? "result" : "input") +"</dataType>");
				for (IdoncPart part : parts) {
					stream.println("	<part>");
					stream.println("		<number>" + part.getNumber()
							+ "</number>");
					stream.println("			<data>");
					if (isResult) {
//						for (IdoncResult result : part.getResult()) {
//							stream.println("				<value>" + result.getValue()
//									+ "</value>");
//						}
						stream.println("				<value>" + part.getResult().getValue()+ "</value>");
					} else {
						for (IdoncLongData data : part.getLongDataList()) {
							stream.println("				<value>" + data.getValue()
									+ "</value>");
						}
					}
					stream.println("			</data>");
					stream.println("	</part>");
				}
				stream.println("</project>");
			}
			stream.close();
			fOS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class ProjectComboBoxModel extends DefaultComboBoxModel {

		private static final long serialVersionUID = 1L;
		private List<IdoncProject> projects;

		public ProjectComboBoxModel(List<IdoncProject> projects) {
			this.projects = projects;
		}

		public Object getElementAt(int index) {
			return projects.get(index);
		}

		public int getSize() {
			return projects.size();
		}

	}

	private class DataTypeComboBoxModel extends DefaultComboBoxModel {

		private static final long serialVersionUID = 1L;
		private List<DataType> types;

		public DataTypeComboBoxModel(List<DataType> types) {
			this.types = types;
		}

		@Override
		public Object getElementAt(int index) {
			return types.get(index);
		}

		@Override
		public int getSize() {
			return types.size();
		}
	}

	private class DataType {
		private int val;
		private String text;

		public DataType(int val, String text) {
			this.val = val;
			this.text = text;
		}

		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}
}
