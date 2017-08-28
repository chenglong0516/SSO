package cn.itcast.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.pojo.LoginPara;
import cn.itcast.pojo.RegistPara;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import cn.itcast.tools.PefConfig;

/*
 * 用于用户注册，登录，注销操作
 */

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PefConfig pefConfig;
	
	@RequestMapping("/regist.action")
	public String regist(RegistPara registPara, HttpServletRequest req){
		try {
			userService.register(registPara.getUsername(), registPara.getPassword());
		} catch (ValidationException e) {
			req.setAttribute("result", e.getMessage());
			return "regist";
		}
		return "redirect:"+pefConfig.getSsoUrl()+"/forward/toLogin.action";
	}
	
	@RequestMapping("/getUserInfo.action")
	public void getUserInfo(HttpServletRequest req, HttpServletResponse res){
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		User user = null;
		try {
			user = userService.findById(id);
			res.getWriter().write(user.toString());
		} catch (ValidationException e) {
			req.setAttribute("result", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/logout.action")
	public String logout(HttpSession session ){
	    if (session != null) {
	        session.invalidate();
	    }
		return "redirect:/";
	}
	
	@RequestMapping("/backLogout.action")
	public void backLogout(HttpSession session, HttpServletResponse res ){
		System.out.println("backLogout.action...");
		if (session != null) {
			String id = session.getId();
			System.out.println(id+" 局部session ....invalidate");
			session.invalidate();
		}
		try {
			res.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
