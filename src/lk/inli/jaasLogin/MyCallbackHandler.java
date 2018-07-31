package lk.inli.jaasLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class MyCallbackHandler implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		NameCallback nameCallback = (NameCallback) callbacks[0];
		System.out.println(nameCallback.getPrompt());
		nameCallback.setName((new BufferedReader(new InputStreamReader(System.in))).readLine());
		PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
		System.out.println(passwordCallback.getPrompt());
		passwordCallback.setPassword((new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray());
	}

}
