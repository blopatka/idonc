package org.lopatka.idonc.dao;


import java.util.List;

import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.data.IdoncResult;

public interface ProjectDao {

	List<IdoncProject> get(int first, int count);

	IdoncProject load(Long id);

	int count();

	void add(IdoncProject project);

	IdoncProject save(IdoncProject project);

	List<IdoncPart> getParts(IdoncProject project);

	IdoncPart getPartWithConfirmation(String username, IdoncProject project);

	IdoncPart getPartWithoutConfirmation(IdoncProject project);

	void returnProcessingResultWithConfirmation(String username, IdoncPart part,
			IdoncResult result);

	void returnProcessingResultWithoutConfirmation(IdoncPart part,
			IdoncResult result);

}
