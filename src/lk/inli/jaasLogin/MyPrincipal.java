package lk.inli.jaasLogin;

import java.io.Serializable;
import java.security.Principal;

public class MyPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 3839989538341076094L;
	private String name;
	
	public MyPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean flag = false;
		
		if (obj instanceof MyPrincipal) flag = this.name.equals(((MyPrincipal) obj).getName());
		return flag;
	}
}
