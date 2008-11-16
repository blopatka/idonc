package org.lopatka.idonc.dao;


import java.util.List;
import org.lopatka.idonc.model.data.IdoncProject;

public interface ProjectDao {

	@SuppressWarnings("unchecked")
	List get(int first, int count);

	IdoncProject load(long id);

	int count();

	void add(IdoncProject project);

}
