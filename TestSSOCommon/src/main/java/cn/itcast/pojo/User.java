package cn.itcast.pojo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 数据库User表对应bean
 */
public class User implements Serializable{

	private static final long serialVersionUID = 8193833949900162757L;

	private Integer id;
	
	private String username;
	
	private String password;

	private Date createTime;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
//	public void setCreateTime(String createTimeStr) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date date = sdf.parse(createTimeStr);
//			this.createTime = date;
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"username\":");
		sb.append(username+",");
		sb.append("\"password\":");
		sb.append(password);
		sb.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("aaa");
		user.setPassword("111");
		System.out.println(user);
	}
}
