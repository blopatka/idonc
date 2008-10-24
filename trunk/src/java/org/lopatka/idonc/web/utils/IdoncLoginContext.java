package org.lopatka.idonc.web.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.strategies.LoginException;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lopatka.idonc.web.dao.UserCredentialDao;
import org.lopatka.idonc.web.dao.UserDao;
import org.lopatka.idonc.web.model.user.UserCredential;
import org.lopatka.idonc.web.page.component.BasePage;

public class IdoncLoginContext extends LoginContext {

	@SpringBean(name="userDao")
	private UserDao userDao;
	
	@SpringBean(name="userCredentialDao")
	private UserCredentialDao userCredentialDao;
	
	private final String username;
	private final String password;
	
	public IdoncLoginContext() {
		super(0, true);
		username = null;
		password = null;
	}
	
	public IdoncLoginContext(String username, String password) {
		super(0, true);
		this.username = username;
		this.password = password;
	}
	
	@Override
	public Subject login() throws LoginException {
		//authenticate username and password, throw exception if not found
//		UserCredential cred = userCredentialDao.get(username);
//		if (cred == null) {
//			throw new LoginException("username did not exist");
//		}
//		String storedHash = cred.getPassword();
//		String storedSalt = cred.getSalt();
//		try {
//			String newHash = PasswordHasher.getHash(1000, password, storedSalt);
//			if(newHash.equals(storedHash)) {
//				DefaultSubject subj = new IdoncPrimarySubject();
//				subj.addPrincipal(new IdoncPrincipal("basic"));
//				
//				return subj;
//			} else {
//				throw new LoginException("password did not match");
//			}
//		} catch (NoSuchAlgorithmException e) {
//			throw new LoginException("login failed - no hash algorithm available");
//		} catch (UnsupportedEncodingException e) {
//			throw new LoginException("login failed - no such encoding");
//		}
		if(username.equals(password)) {
			DefaultSubject subj = new IdoncPrimarySubject();
			subj.addPrincipal(new IdoncPrincipal("basic"));
			return subj;
		}
		throw new LoginException("passw and login did not match");
	}
	
	@Override
	public boolean preventsAdditionalLogins() {
		return false;
	}
	
	private static final class IdoncPrimarySubject extends DefaultSubject {

		private static final long serialVersionUID = 175351534459246324L;

		@Override
		public boolean isClassAuthenticated(Class class1) {
			return BasePage.class.isAssignableFrom(class1);
		}
		
		@Override
		public boolean isComponentAuthenticated(Component component) {
			if (component instanceof Page) {
				return isClassAuthenticated(component.getClass());
			}
			return true;
		}
		
		@Override
		public boolean isModelAuthenticated(IModel model, Component component) {
			return true;
		}
	}

}
