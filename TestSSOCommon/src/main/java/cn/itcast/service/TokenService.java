package cn.itcast.service;

import java.util.Map;

import javax.xml.bind.ValidationException;

import cn.itcast.pojo.Token;

/*
 * 用户相关Service
 */

public interface TokenService {

	public void insert(Integer id, String token) throws ValidationException;

	public Token find(Map<String,Object> map) throws ValidationException;
	
	public void update(String tokenValue, String state, String loginUrl) throws ValidationException;
	
	public void delete(String tokenValue) throws ValidationException;

}
