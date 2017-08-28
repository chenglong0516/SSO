package cn.itcast.dao.mapper;

import java.util.Map;

import cn.itcast.pojo.User;

public interface UserMapper {
	
    public int insert(User user);
    
    public User findByUserName(Map<String,Object> conditionMap);
    
    public User findById(Map<String,Object> conditionMap);

}
