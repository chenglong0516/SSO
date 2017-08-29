package cn.itcast.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.xml.bind.ValidationException;

import org.apache.http.Header;

import basic.arch.component.http.builder.HCB;
import basic.arch.component.http.client.HttpClientUtil;
import basic.arch.component.http.common.HttpConfig;
import basic.arch.component.http.common.HttpHeader;
import basic.arch.component.http.common.HttpMethods;
import cn.itcast.pojo.Token;

/**
 * Application Lifecycle Listener implementation class LogoutListener
 * session监听器, 如果session销毁则执行
 */
public class LogoutListener implements HttpSessionListener {

	/**
	 * Default constructor.
	 */
	public LogoutListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session 销毁，执行相应代码。。。。");
		HttpSession session = se.getSession();
		Token findToken = (Token) session.getAttribute("token");
		if (null != findToken.getLoginUrl()	&& !"".equals(findToken.getLoginUrl())) {
			String[] list = findToken.getLoginUrl().split(";");
			for (String loginUrl : list) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						sendBackLogout(loginUrl);
					}
				}).start();
			}
		}
	}

	/*
	 * 向各个注册系统发送注销请求
	 */
	private void sendBackLogout(String loginUrl) {
		try {
			String[] list1 = loginUrl.split("===");
			String url = list1[0];
			String JSESSIONID = list1[1];
			String cookies = "JSESSIONID=" + JSESSIONID;
			Header[] headers = null;
			headers = HttpHeader.custom().cookie(cookies).build();
			HCB hcb = HCB.custom().timeout(30000);
			HttpConfig httpConfig = HttpConfig.custom()
					.url(url + "/user/backLogout.action")
					.headers(headers, true)
					.hcb(hcb)
					.encoding("utf-8")
					.method(HttpMethods.POST);
			String response = HttpClientUtil.post(httpConfig);
			if (!"success".equals(response)) {
				System.out.println(url + "退出失败");
			}
		} catch (Exception e) {
			// return false;
		}
	}

}
