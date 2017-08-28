package cn.itcast.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import basic.arch.component.http.builder.HCB;
import basic.arch.component.http.client.HttpClientUtil;
import basic.arch.component.http.common.HttpConfig;
import basic.arch.component.http.common.HttpMethods;
import cn.itcast.pojo.Token;
import cn.itcast.service.TokenService;
import cn.itcast.tools.PefConfig;

import com.alibaba.fastjson.JSONObject;

/**
 * 过滤器类
 */
@Component
public class LoginFilter implements Filter{
    
    public FilterConfig config;
    
	@Autowired
	private PefConfig pefConfig;
	
	@Autowired
	private TokenService tokenService;
	
    public void destroy() {
        this.config = null;
    }

    public static boolean isContains(String container, String[] regx) {
        boolean result = false;

        for (int i = 0; i < regx.length; i++) {
            if (container.indexOf(regx[i]) != -1) {
                return true;
            }
        }
        return result;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest)request;
        HttpServletResponse hresponse = (HttpServletResponse)response;

        String includeStrings = "toAccount;getUserInfo;logout;others";    // 过滤资源后缀参数
        String disabletestfilter = "N";// 过滤器是否有效

        if (disabletestfilter.toUpperCase().equals("Y")) {    // 过滤无效
            chain.doFilter(request, response);
            return;
        }
        String[] includeList = includeStrings.split(";");

        Object isLogin = hrequest.getSession().getAttribute("isLogin");//判断用户是否登录

        if (!this.isContains(hrequest.getRequestURI(), includeList)) {// 只对指定过滤参数后缀进行过滤
        	//如果是toLogin，测判断是否有全局会话session
        	if(hrequest.getRequestURI().indexOf("toLogin")>-1){//如果是跳转登录请求
        		if(null!=isLogin && (boolean)isLogin){//如果已经登录
        			//获取用户id
	            	String id = (String) hrequest.getSession().getAttribute("id");
	            	//拿到数据库中token
	            	Token token = null;
					try {
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("id", Integer.parseInt(id));
						token = tokenService.find(map);
					} catch (ValidationException e) {
						e.printStackTrace();
					}
	            	//获取目的链接
	        		String fromUrl = hrequest.getParameter("fromUrl");
	        		//重定向到要去的页面
	        		if(null!=fromUrl){
	        			hresponse.sendRedirect(fromUrl+"?token="+token.getToken()+"&id="+id);
	        			return;
	        		}
        		}else{
        			//跳转登录页面并且没有登录，进入登录页面
        			chain.doFilter(request, response);
        		}
        	}else{
        		//不需要拦截请求，也不是登录请求，直接放过
        		chain.doFilter(request, response);
        	}
            return;
        }

        if(null!=isLogin && (boolean)isLogin){
        	if(hrequest.getRequestURI().indexOf("logout")>-1){//如果是跳转登录请求
        		String token = (String) hrequest.getSession().getAttribute("token");
            	hresponse.sendRedirect(pefConfig.getSsoUrl() 
        				+ "/user/ssoLogout.action"
        				+ "?token="+token+"&fromUrl="+pefConfig.getDomainUrl()
        				);
            	return;
        	}
        	chain.doFilter(request, response);
        }else{
            // 请求附带token参数
        	boolean verifyResult = false;
            String token = hrequest.getParameter("token");
            String id = hrequest.getParameter("id");
            if (token != null) {
                // 去sso认证中心校验token
                verifyResult = this.verify(hrequest, pefConfig.getDomainUrl(), token);//注册地址，令牌
            }
            if (verifyResult) {
            	hrequest.getSession().setAttribute("isLogin", true);
            	hrequest.getSession().setAttribute("id", id);
            	hrequest.getSession().setAttribute("token", token);
                chain.doFilter(request, response);
            }else{
            	boolean isAjaxRequest = isAjaxRequest(hrequest);
            	if(isAjaxRequest){
            		hrequest.setCharacterEncoding("UTF-8");
            		hresponse.setContentType("text/html;charset=utf-8");
            		JSONObject json = new JSONObject();
            		json.put("msg", "请重新登录！");
            		hresponse.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
            	}else{
            		hresponse.sendRedirect(pefConfig.getSsoUrl() 
            				+ "/forward/toLogin.action"
            				+ "?fromUrl="+hrequest.getRequestURL()
            				);
            	}
            }
        }
        return;
    }
    
    public boolean verify(HttpServletRequest request, String url, String token){
//        Header[] headers = CookieConvert.servletCookieToHttpClientHeaders(request);
//        if(headers==null){
//            return false;
//        }
        try {
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("url",url);
            map.put("token",token);
            map.put("JSESSIONID",request.getSession().getId());//客户端cookie
            HCB hcb = HCB.custom().timeout(30000);
            HttpConfig httpConfig =HttpConfig.custom()
                    .url(pefConfig.getSsoUrl()+"/sso/verify.action")
//                    .headers(headers, true)
                    .map(map)
                    .hcb(hcb)
                    .encoding("utf-8")
                    .method(HttpMethods.POST);
            String response = HttpClientUtil.post(httpConfig);
            if("effective".equals(response)){
            	return true;
            }
        } catch (Exception e) {
        	return false;
        }
    	return false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }
    
	/**
	 * 
	 * @Description:判断是否ajax请求
	 * @author shl
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		return requestType != null && requestType.equals("XMLHttpRequest");
	}
	
}
