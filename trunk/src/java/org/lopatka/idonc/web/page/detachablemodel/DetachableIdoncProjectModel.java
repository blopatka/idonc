package org.lopatka.idonc.web.page.detachablemodel;

import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.web.model.data.IdoncProject;
import org.lopatka.idonc.web.service.IdoncService;

public class DetachableIdoncProjectModel extends LoadableDetachableModel {

	private static final long serialVersionUID = 584698078139791121L;

	private final long id;
	
	private final IdoncService service;

	public DetachableIdoncProjectModel(IdoncProject project,
			IdoncService idoncService) {
		super(project);
		this.id = project.getId();
		this.service = idoncService;
	}

	@Override
	protected Object load() {
		return service.loadProject(id);
	}
	
	

}
