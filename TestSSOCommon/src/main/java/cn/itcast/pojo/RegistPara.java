package cn.itcast.pojo;

import java.io.Serializable;

/*
 * ”√ªß◊¢≤·«Î«ÛBean
 */
public class RegistPara implements Serializable{

	private static final long serialVersionUID = 3581327635300144211L;

	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RegistPara [username=" + username + ", password=" + password
				+ "]";
	}
}
