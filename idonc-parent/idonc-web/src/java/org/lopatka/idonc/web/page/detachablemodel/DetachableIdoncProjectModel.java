package org.lopatka.idonc.web.page.detachablemodel;

import org.apache.wicket.model.LoadableDetachableModel;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncSession;

public class DetachableIdoncProjectModel extends LoadableDetachableModel {

	private static final long serialVersionUID = 1L;

	private final Long id;

	private final IdoncService service;

    private IdoncSession session = IdoncSession.get();

	public DetachableIdoncProjectModel(IdoncProject project,
			IdoncService idoncService) {
		super(project);
		this.id = project.getId();
		this.service = idoncService;
	}

	@Override
	protected Object load() {
        String username = session.getLoggedUserName();
        String sessionId = session.getSessionId();
		return service.loadProject(username, sessionId, id);
	}



}
