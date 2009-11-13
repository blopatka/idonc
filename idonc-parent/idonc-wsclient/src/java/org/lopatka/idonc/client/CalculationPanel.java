package org.lopatka.idonc.client;

import info.clearthought.layout.TableLayout;

import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.lopatka.idonc.computation.ComputationManager;
import org.lopatka.idonc.computation.IComputation;
import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.IdoncResult;

public class CalculationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private AppSession session = AppSession.getInstance();

	private JLabel projectNameText;

	private JLabel projectNameLabel;

	private JLabel projectWebsiteText;

	private JLabel projectWebsiteLabel;

	private JLabel partNameLabel;

	private JLabel partNameText;

	private JLabel partNumberLabel;

	private JLabel partNumberText;

	private JTable partValuesGrid;

	public CalculationPanel() {
		setBounds(0, 0, 400, 400);
		double size[][] = {
				{ 10, 100, 10, 200, TableLayout.FILL, 10 },
				{ 10, 20, 10, 20, 10, 20, 10, 20, 10, TableLayout.FILL,
						10 } };
		setLayout(new TableLayout(size));
		initComponents();
	}

	private void initComponents() {
		ResourceMap resourceMap = Application.getInstance(MainIdoncApp.class)
		.getContext().getResourceMap(CalculationPanel.class);

		projectNameLabel = new JLabel();
		projectNameText = new JLabel();
		projectWebsiteLabel = new JLabel();
		projectWebsiteText = new JLabel();
		partNameLabel = new JLabel();
		partNameText = new JLabel();
		partNumberLabel = new JLabel();
		partNumberText = new JLabel();
		String data[][] = {{}};
		String col[] = {resourceMap.getString("partValues.grid.columnName.id"), resourceMap.getString("partValues.grid.columnName.value")};
		DefaultTableModel model = new DefaultTableModel(data, col);
		partValuesGrid = new JTable(model);



		projectNameLabel.setName(resourceMap.getString("projectName.label.name"));
		projectNameLabel.setText(resourceMap.getString("projectName.label.text"));
		add(projectNameLabel, "1, 1");

		projectNameText.setName(resourceMap.getString("projectName.text.name"));
		add(projectNameText, "3, 1");

		projectWebsiteLabel.setName(resourceMap.getString("projectWebsite.label.name"));
		projectWebsiteLabel.setText(resourceMap.getString("projectWebsite.label.text"));
		add(projectWebsiteLabel, "1, 3");

		projectWebsiteText.setName(resourceMap.getString("projectWebsite.text.name"));
		add(projectWebsiteText, "3, 3");

		partNameLabel.setName(resourceMap.getString("partName.label.name"));
		partNameLabel.setText(resourceMap.getString("partName.label.text"));
		add(partNameLabel, "1, 5");

		partNameText.setName(resourceMap.getString("partName.text.name"));
		add(partNameText, "3, 5");

		partNumberLabel.setName(resourceMap.getString("partNumber.label.name"));
		partNumberLabel.setText(resourceMap.getString("partNumber.label.text"));
		add(partNumberLabel, "1, 7");

		partNumberText.setName(resourceMap.getString("partNumber.text.name"));
		add(partNumberText, "3, 7");


		partValuesGrid.setName(resourceMap.getString("partValues.grid.name"));
		JScrollPane gridScroll = new JScrollPane(partValuesGrid);
		add(gridScroll, "1, 9, 4, 9");
	}

	public void refresh() {
		IdoncProject project = session.getProject();
		projectNameText.setText(project.getName());
		projectWebsiteText.setText(project.getWebsite());
	}

	public void loadPart() {

		IComputation algorithm = ComputationManager.getInstance().getComputationForProject(session.getProject());
		algorithm.isResultConfirmationRequired();

		//TODO begin calculations !!
		CalculationThread thr = new CalculationThread(algorithm);
		thr.execute();

	}

	private class CalculationThread extends SwingWorker<Boolean, Object>{

		private IComputation algorithm;

		public CalculationThread(IComputation algorithm) {
			this.algorithm = algorithm;
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			String username = session.getLoggedUser().getUser().getUserName();
			String sessionId = session.getLoggedUser().getSessionId();
			boolean requiresConfirmation = algorithm.isResultConfirmationRequired();
			for(;;){
				IdoncPart part = AppSession.idoncService.getPartToProcess(username, sessionId, requiresConfirmation);

				ResourceMap resourceMap = Application.getInstance(MainIdoncApp.class)
				.getContext().getResourceMap(CalculationPanel.class);
				partValuesGrid.setModel(new IdoncPartTableModel(part, resourceMap));

				IdoncResult result = algorithm.computeData(part);
				AppSession.idoncService.returnProcessingResult(username, sessionId, part, result, requiresConfirmation);
				if(session.isCalculationInterrupted()) {
					break;
				}
			}
			return false;
		}

		@Override
		protected void done() {
			try {
				session.setCalculationInterrupted(get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException ignore) {
			}
		}



	}
}
