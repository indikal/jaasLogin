package lk.inli.jaasLogin;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class MyLoginModule implements LoginModule {
	public static final String[][] TEST_USERS = {{"user1", "password1"}, {"user2", "password2"}, {"user3", "password3"}}; 
	
	private Subject subject;
	private CallbackHandler callbackHandler;
	private MyPrincipal myPrincipal;
	
	@Override
	public boolean abort() throws LoginException {
		if (null != this.subject && null != this.myPrincipal && !subject.getPrincipals().contains(this.myPrincipal)) {
			this.subject.getPrincipals().remove(this.myPrincipal);
		}
		this.subject = null;
		this.myPrincipal = null;
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		boolean flag = false;
		
		if (null != this.subject && !subject.getPrincipals().contains(this.myPrincipal)) {
			this.subject.getPrincipals().add(this.myPrincipal);
			flag = true;
		}
		return flag;
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> arg2, Map<String, ?> arg3) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}

	@Override
	public boolean login() throws LoginException {
		boolean flag = false;
		Callback[] callbackArray = new Callback[2];
		callbackArray[0] = new NameCallback("User Name:");
		callbackArray[1] = new PasswordCallback("password:", false);
		
		try {
			this.callbackHandler.handle(callbackArray);
			String userName = ((NameCallback) callbackArray[0]).getName();
			String password = new String(((PasswordCallback) callbackArray[1]).getPassword());
			
			int i = 0;
			while (i < TEST_USERS.length) {
				if (TEST_USERS[i][0].equals(userName) && TEST_USERS[i][1].equals(password)) {
					myPrincipal = new MyPrincipal(userName);
					System.out.println("Authentication success ...");
					flag = true;
					break;
				}
				i++;
			}
			
			if (!flag) throw new FailedLoginException("Authentication failure ...");
		} catch (IOException | UnsupportedCallbackException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean logout() throws LoginException {
		this.subject.getPrincipals().remove(myPrincipal);
		this.subject = null;
		return true;
	}

}
