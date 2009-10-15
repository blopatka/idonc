package org.lopatka.idonc.client;

import org.lopatka.idonc.model.user.LoggedUser;

public class AppSession {

	final static String MAIN_PANEL = "Main Panel";
	final static String LOGIN_PANEL = "Login Panel";
	final static String CALCULATION_PANEL = "Calculation Panel";

	private LoggedUser loggedUser;

	private AppSession() {}

	private static class AppSessionHolder {
		private static final AppSession INSTANCE  = new AppSession();
	}

	public static AppSession getInstance() {
		return AppSessionHolder.INSTANCE;
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}
}
