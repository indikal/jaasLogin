package lk.inli.jaasLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Driver {

	public enum Action {action1, action2, logout};
	
	public static void main(String[] args) {
		Driver driver = new Driver();
		System.setProperty("java.security.auth.login.config", "myJaas.config");
		LoginContext loginContext = null;
		
		while (true) {
			
			try {
				loginContext = new LoginContext("MyJaasLoginConfig", new MyCallbackHandler());
				loginContext.login();
				boolean flag = true;
				while (flag) {
					flag = driver.performAction(loginContext);
				}
			} catch (LoginException | IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	boolean performAction(LoginContext loginContext) throws IOException, LoginException {
		boolean flag = false;
		System.out.println("Please specify an action to take (usage: action1, action2, logout)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			switch (Action.valueOf(br.readLine())) {
				case logout:
					loginContext.logout();
					System.out.println("You are now logged out ...");
					flag = false;
					break;
				case action1:
					PrivilegedAction<Object> privilegedAction1 = () -> {
						System.out.println("Action1 was performed");
						return null;
					};
					Subject.doAs(loginContext.getSubject(), privilegedAction1);
					System.out.println("by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
					flag = true;
					break;
				case action2:
					PrivilegedAction<Object> privilegedAction2 = () -> {
						System.out.println("Action2 was performed");
						return null;
					};
					Subject.doAs(loginContext.getSubject(), privilegedAction2);
					System.out.println("by " + loginContext.getSubject().getPrincipals().iterator().next().getName());
					flag = true;
					break;
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid entry ...");
			flag = true;
		}
		return flag;
	}
}
