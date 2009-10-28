package org.idon.computation;

import org.lopatka.idonc.exception.IdoncException;
import org.lopatka.idonc.model.data.IdoncProject;

public class ComputationManager {

	private ComputationManager() {
	};

	private static class ComputationManagerHolder {
		private static final ComputationManager INSTANCE = new ComputationManager();
	}

	public static ComputationManager getInstance() {
		return ComputationManagerHolder.INSTANCE;
	}

	public IComputation getComputationForProject(IdoncProject project) throws IdoncException {
		try {
			Class impl = Class.forName(project.getComputationClassName());
			return (IComputation) impl.newInstance();
		} catch (Exception e) {
			throw new IdoncException("blad podczas pobierania klasy wykonujacej obliczenia");
		}
	}
}
