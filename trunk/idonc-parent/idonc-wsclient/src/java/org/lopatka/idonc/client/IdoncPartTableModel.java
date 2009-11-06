package org.lopatka.idonc.client;

import javax.swing.table.AbstractTableModel;

import org.jdesktop.application.ResourceMap;
import org.lopatka.idonc.model.data.IdoncPart;

public class IdoncPartTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private IdoncPart part;
	private ResourceMap resource;

	public IdoncPartTableModel(IdoncPart part, ResourceMap resMap) {
		this.part = part;
		this.resource = resMap;
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return part.getLongDataList().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return part.getLongDataList().get(rowIndex).getId();
		case 1:
			return part.getLongDataList().get(rowIndex).getValue();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return resource.getString("partValues.grid.columnName.id");
		case 1:
			return resource.getString("partValues.grid.columnName.value");
		default:
			return "";
		}
	}

}
