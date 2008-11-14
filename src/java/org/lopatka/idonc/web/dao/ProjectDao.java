package org.lopatka.idonc.web.dao;

import java.util.Iterator;

import org.lopatka.idonc.web.model.data.IdoncProject;

public interface ProjectDao {

	@SuppressWarnings("unchecked")
	Iterator get(int first, int count);

	IdoncProject load(long id);

	int count();

	void add(IdoncProject project);

}
