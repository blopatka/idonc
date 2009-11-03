package org.lopatka.idonc.dao;


import java.util.List;

import org.lopatka.idonc.model.data.IdoncPart;
import org.lopatka.idonc.model.data.IdoncProject;
import org.lopatka.idonc.model.user.IdoncUser;

public interface ProjectDao {

	@SuppressWarnings("unchecked")
	List get(int first, int count);

	IdoncProject load(int id);

	int count();

	void add(IdoncProject project);

	IdoncProject save(IdoncProject project);

	List<IdoncPart> getParts(IdoncProject project);

}
