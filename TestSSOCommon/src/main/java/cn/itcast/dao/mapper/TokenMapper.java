package cn.itcast.dao.mapper;

import java.util.Map;

import cn.itcast.pojo.Token;

public interface TokenMapper {
	
    public int insert(Token token);
    
    public Token find(Map<String,Object> conditionMap);
    
    public int update(Token token);

	public void delete(String tokenValue);

}
