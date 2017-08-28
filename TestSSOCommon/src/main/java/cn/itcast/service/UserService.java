package cn.itcast.service;

import javax.xml.bind.ValidationException;

import cn.itcast.pojo.User;

/*
 * �û����Service
 */

public interface UserService {

	public void register(String username, String password) throws ValidationException;

	public User login(String username, String password) throws ValidationException;

	public User findById(String id) throws ValidationException;
	
}
