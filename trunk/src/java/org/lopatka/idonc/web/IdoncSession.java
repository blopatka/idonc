package org.lopatka.idonc.web;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.dao.UserCredentialDao;
import org.lopatka.idonc.web.model.user.UserCredential;
import org.lopatka.idonc.web.utils.PasswordHasher;

public class IdoncSession extends AuthenticatedWebSession {

	@SpringBean(name="userCredentialDao")
	private UserCredentialDao userCredentialDao;
	
	private static final long serialVersionUID = -3623974092048074997L;
	
	public IdoncSession(Request request) {
		super(request);
		//http://markmail.org/message/2tqrkaik3ym6nif3#query:wicket%20authenticatedwebsession%20dao+page:1+mid:trzow66cod7ebomi+state:results
		InjectorHolder.getInjector().inject(this);
	}
	
	public void setUserCredentialDao(UserCredentialDao dao) {
		this.userCredentialDao = dao;
	}
	
	public static final IdoncSession getSession() {
		return (IdoncSession) Session.get();
	}

	@Override
	public boolean authenticate(String username, String password) {
		//return username.equals(password);
		if((username != null) && (password != null)) {
			UserCredential cred = userCredentialDao.get(username);
			byte[] storedHash = PasswordHasher.base64ToByte(cred.getPassword());
			byte[] storedSalt = PasswordHasher.base64ToByte(cred.getSalt());
			try {
				byte[] inputHash = PasswordHasher.getHash(1000, password, storedSalt);
				if(Arrays.equals(inputHash, storedHash)) {
					return true;
				} else {
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				return false;
			} catch (UnsupportedEncodingException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Roles getRoles() {
		if(isSignedIn()) {
			return new Roles(Roles.ADMIN);
		}
		return null;
	}

}
