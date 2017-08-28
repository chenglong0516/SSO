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
	public String logout(HttpSession session, HttpServletRequest req, HttpServletResponse res){
		System.out.println("�ͻ���ע��,���͹���������");
	    if (session != null) {
	    	System.out.println(session.getId()+" ȫ��session invalidate....");
	        session.invalidate();//����LogoutListener
	        //�������й���
	        try {
	        	String tokenValue = req.getParameter("token");
				Map<String,Object> conditionMap = new HashMap<String, Object>();
				conditionMap.put("token", tokenValue);
				Token findToken = tokenService.find(conditionMap);//�����ݿ����õ�token
				if(null!=findToken.getLoginUrl() && !"".equals(findToken.getLoginUrl())){
					String[] list = findToken.getLoginUrl().split(";");
					for (String loginUrl : list) {
						try {
							String[] list1 = loginUrl.split("===");
							String url = list1[0];
							String JSESSIONID = list1[1];
//				            Cookie cookie = new Cookie("JSESSIONID",JSESSIONID);
//				            cookie.setDomain(url);
//				            cookie.setPath("/");
//				            String cookies= req.getHeader("Cookie");
				            String cookies= "JSESSIONID="+JSESSIONID;
				            Header[] headers=null;
				            if(cookies!=null){
				                headers = HttpHeader.custom().cookie(cookies).build();
				            }
				    		HCB hcb = HCB.custom().timeout(30000);
				    		HttpConfig httpConfig =HttpConfig.custom()
				    				.url(url+"/user/backLogout.action")
				    				.headers(headers, true)
				    				.hcb(hcb)
				    				.encoding("utf-8")
				    				.method(HttpMethods.POST);
				    		String response = HttpClientUtil.post(httpConfig);
				    		if(!"success".equals(response)){
				    			System.out.println(url+"�˳�ʧ��");
				    		}
				    	} catch (Exception e) {
//				    		return false;
				    	}
					}
				}
				tokenService.delete(req.getParameter("token"));
			} catch (ValidationException e) {
				e.printStackTrace();
			}
	    }
	    return "redirect:"+req.getParameter("fromUrl");
	}
	
}
