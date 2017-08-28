package cn.itcast.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.pojo.Token;
import cn.itcast.service.TokenService;
import cn.itcast.service.UserService;

/*
 * 用于用户注册，登录，注销操作
 */

@Controller
@RequestMapping("/sso")
public class SSOController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/verify.action")
	public void login(HttpServletRequest req, HttpServletResponse res, HttpSession session ){
		String url = req.getParameter("url");
		String token = req.getParameter("token");
		String JSESSIONID = req.getParameter("JSESSIONID");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("token", token);
			Token findToken = tokenService.find(map);//令牌有效,无效抛出异常
			
			StringBuilder newUrlList = new StringBuilder();
			if(null!=findToken.getLoginUrl() && !"".equals(findToken.getLoginUrl())){
				newUrlList.append(findToken.getLoginUrl());
			}
			newUrlList.append(url);
			newUrlList.append("===");
			newUrlList.append(JSESSIONID);
			newUrlList.append(";");
			tokenService.update(token, "0", newUrlList.toString());
			res.getWriter().write("effective");
		} catch (ValidationException e) {//令牌无效
			try {
				res.getWriter().write("invalid");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
