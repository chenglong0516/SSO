package cn.itcast.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import basic.arch.component.http.builder.HCB;
import basic.arch.component.http.client.HttpClientUtil;
import basic.arch.component.http.common.HttpConfig;
import basic.arch.component.http.common.HttpHeader;
import basic.arch.component.http.common.HttpMethods;
import cn.itcast.pojo.LoginPara;
import cn.itcast.pojo.Token;
import cn.itcast.pojo.User;
import cn.itcast.service.TokenService;
import cn.itcast.service.UserService;

/*
 * �����û�ע�ᣬ��¼��ע������
 */

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/login.action")
	public String login(LoginPara loginPara, HttpServletRequest req, HttpSession session ){
		User user = null;
		try {
			user = userService.login(loginPara.getUsername(), loginPara.getPassword());
		
		
			//����ȫ�ֻỰ�ĵ�¼״̬
			session.setAttribute("isLogin", true);
			session.setAttribute("id", user.getId().toString());
			
			String fromUrl = req.getParameter("fromUrl");
			//��������
			String token = UUID.randomUUID().toString();
			//���ƴ������ݿ�,0��ʾ��Ч
			tokenService.insert(user.getId(), token);
			
			//��������ص�¼���ض���Ҫȥ��ҳ��
			if(null!=fromUrl && !"".equals(fromUrl)){
				return "redirect:"+fromUrl+"?token="+token+"&id="+user.getId();
			}else{
				return "redirect:/";
			}
		} catch (ValidationException e) {
			req.setAttribute("result", e.getMessage());
			return "login";
		}
	}
	
	@RequestMapping("/ssoLogout.action")
	public String logout(HttpSession session, HttpServletRequest req){
		Token token = null;
	    if (session != null) {
	    	System.out.println(session.getId()+" ȫ��session invalidate....");
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("token", req.getParameter("token"));
	    	try {
				token = tokenService.find(map);
				session.setAttribute("token", token);//token����session����������
				session.invalidate();//����LogoutListener
				tokenService.delete(req.getParameter("token"));//ɾ�����ݿ�
			} catch (ValidationException e) {
				e.printStackTrace();
			}
	    }
	    return "redirect:"+req.getParameter("fromUrl");
	}
	
}
