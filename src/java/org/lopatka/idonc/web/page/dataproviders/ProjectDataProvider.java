package org.lopatka.idonc.web.page.dataproviders;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.lopatka.idonc.web.model.data.IdoncProject;
import org.lopatka.idonc.web.page.detachablemodel.DetachableIdoncProjectModel;
import org.lopatka.idonc.web.service.IdoncService;

public class ProjectDataProvider implements IDataProvider {

	private static final long serialVersionUID = -2376251935906010691L;

	private IdoncService idoncService;

	public ProjectDataProvider(IdoncService idoncService) {
		this.idoncService = idoncService;
	}

	@SuppressWarnings("unchecked")
	public Iterator iterator(int first, int count) {
		return idoncService.getProjects(first, count);
	}

	public IModel model(Object object) {
		if (object instanceof IdoncProject) {
			IdoncProject project = (IdoncProject) object;
			return new DetachableIdoncProjectModel(project, idoncService);
		}
		throw new IllegalArgumentException("object should be IdoncProject type");
	}

	public int size() {
		return idoncService.countProjects();
	}

	public void detach() {
		
	}

}
