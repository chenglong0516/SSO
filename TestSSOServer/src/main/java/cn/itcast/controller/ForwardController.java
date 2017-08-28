package cn.itcast.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * ����ת������Ӧjspҳ��
 */

@Controller
@RequestMapping("/forward")
public class ForwardController {
	
	@RequestMapping("/toLogin.action")
	public String toLogin(HttpServletRequest request){
		String fromUrl = request.getParameter("fromUrl");
		request.setAttribute("fromUrl", fromUrl);
		return "login";
	}

}
