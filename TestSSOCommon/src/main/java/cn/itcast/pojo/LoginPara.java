package cn.itcast.pojo;

import java.io.Serializable;

/*
 * ”√ªßµ«¬º«Î«ÛBean
 */
public class LoginPara implements Serializable{

	private static final long serialVersionUID = 3703166520982568153L;

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
		return "LoginPara [username=" + username + ", password=" + password
				+ "]";
	}
}
