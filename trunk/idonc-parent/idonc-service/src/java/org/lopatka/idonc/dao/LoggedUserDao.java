package org.lopatka.idonc.dao;

import org.lopatka.idonc.model.user.IdoncUser;
import org.lopatka.idonc.model.user.LoggedUser;

public interface LoggedUserDao {

	public LoggedUser getLoggedUserBySession(String sessionId);

	public LoggedUser getLoggedUserByUser(IdoncUser user);

	public void deleteLoggedUser(LoggedUser loggedUser);

	public boolean deleteLoggedUserById(Long id);

	public LoggedUser createLoggedUser(IdoncUser user);

	public void resetAbandonedSessions();
}
