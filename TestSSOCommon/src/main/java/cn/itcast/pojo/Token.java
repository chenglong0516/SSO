package cn.itcast.pojo;

import java.io.Serializable;

/*
 * 数据库Token表对应bean
 */
public class Token implements Serializable{

	private static final long serialVersionUID = -8549005314609815927L;

	private Integer id;
	
	private String token;		//令牌
	
	private String state;		//令牌状态 0有效 1失效
	
	private String loginUrl;	//已登录系统集合

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", token=" + token + ", state=" + state
				+ "]";
	}
	
}
