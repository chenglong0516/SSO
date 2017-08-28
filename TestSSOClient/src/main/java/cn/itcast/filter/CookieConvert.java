package cn.itcast.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;

import basic.arch.component.http.common.HttpHeader;


/**
 * ���ڽ�Servlet�е�cookieת��ΪhttpClient�е�cookie
 * @author jannal
 *
 */
public class CookieConvert {
    public static Header[] servletCookieToHttpClientHeaders(HttpServletRequest request, String jsessionid){
        String cookies= request.getHeader("Cookie");
        if(null!=jsessionid && ""!=jsessionid){
        	System.out.println("�滻cookie");
        }
        Header[] header=null;
        if(cookies!=null){
            header = HttpHeader.custom().cookie(cookies).build();
        }
        return header;
    }
}
