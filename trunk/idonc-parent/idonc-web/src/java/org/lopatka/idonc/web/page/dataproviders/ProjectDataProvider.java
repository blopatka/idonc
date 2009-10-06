package org.lopatka.idonc.web.page.dataproviders;

import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.web.page.detachablemodel.DetachableIdoncProjectModel;
import org.lopatka.idonc.service.IdoncService;
import org.lopatka.idonc.web.IdoncSession;

public class ProjectDataProvider implements IDataProvider {

	private static final long serialVersionUID = 1L;

	private IdoncService idoncService;

    private IdoncSession session = IdoncSession.get();

	public ProjectDataProvider(IdoncService idoncService) {
		this.idoncService = idoncService;
	}

	@SuppressWarnings("unchecked")
	public Iterator iterator(int first, int count) {
        String username = session.getLoggedUserName();
        String sessionId = session.getSessionId();
        return idoncService.getProjects(username, sessionId, first, count).iterator();
      
	}

	public IModel model(Object object) {
		if (object instanceof IdoncProject) {
			IdoncProject project = (IdoncProject) object;
			return new DetachableIdoncProjectModel(project, idoncService);
		}
		throw new IllegalArgumentException("object should be IdoncProject type");
	}

	public int size() {
        String username = session.getLoggedUserName();
        String sessionId = session.getSessionId();
		return idoncService.countProjects(username, sessionId);
	}

	public void detach() {
		
	}

}
