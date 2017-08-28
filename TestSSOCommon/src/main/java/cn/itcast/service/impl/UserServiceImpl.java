package cn.itcast.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.mapper.UserMapper;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired 
	private UserMapper userMapper;
	
	@Override
	public void register(String username, String password) throws ValidationException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		int insert = userMapper.insert(user);
		if(insert!=1){
			throw new ValidationException("×¢²áÊ§°Ü");
		}
	}

	@Override
	public User login(String username, String password) throws ValidationException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		User user = userMapper.findByUserName(map);
		if(user==null || !user.getPassword().equals(password)){
			throw new ValidationException("µÇÂ¼Ê§°Ü");
		}
		return user;
	}
	
	@Override
	public User findById(String id) throws ValidationException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Integer.parseInt(id));
		User user = userMapper.findById(map);
		return user;
	}

}
