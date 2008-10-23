package org.lopatka.idonc.web;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.WaspSession;

public class IdoncSession extends WaspSession{

	private static final long serialVersionUID = -3623974092048074997L;

	public IdoncSession(WaspApplication application, Request request) {
		super(application, request);
		
	}
	
	public static final IdoncSession getSession() {
		return (IdoncSession) Session.get();
	}

}
