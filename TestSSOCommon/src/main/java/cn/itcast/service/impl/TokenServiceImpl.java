package cn.itcast.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.mapper.TokenMapper;
import cn.itcast.pojo.Token;
import cn.itcast.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired 
	private TokenMapper tokenMapper;

	@Override
	public void insert(Integer id, String tokenValue) throws ValidationException {
		Token token = new Token();
		token.setId(id);
		token.setToken(tokenValue);
		token.setState("0");//有效
		int insert = tokenMapper.insert(token);
		if(insert!=1){
			throw new ValidationException("token插入失败");
		}
	}

	@Override
	public void update(String tokenValue, String state, String loginUrl) throws ValidationException {
		Token token = new Token();
		token.setToken(tokenValue);
		token.setState(state);
		token.setLoginUrl(loginUrl);
		int update = tokenMapper.update(token);
		if(update!=1){
			throw new ValidationException("token修改失败");
		}
		
	}

	@Override
	public Token find(Map<String,Object> map) throws ValidationException {
		Token token = tokenMapper.find(map);
		if(token==null){
			throw new ValidationException("数据库无此token");
		}
		if(token!=null && !token.getState().equals("0")){
			throw new ValidationException("token失效");
		}
		return token;
	}

	@Override
	public void delete(String tokenValue) throws ValidationException {
		tokenMapper.delete(tokenValue);
	}

}
