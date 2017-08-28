<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@include file="/WEB-INF/jsp/header.jsp"%>
<script type="text/javascript" src="/static/jquery-1.11.1.js"></script>
<script type="text/javascript">
    ///////////////////////////////////////////////////////////////////////
    $(function sub() {
        $(document).ready(function() {
        	getUserInfo();
/*              $('#btnGetUserInfo').click(function () {
            	 getUserInfo();
            }) */
        })
         
        function getUserInfo() {
             
            $.ajax({ 
                //客户端向服务器发送请求时采取的方式
                type: "post", 
                 
                cache: false, 
                 
                //服务器返回的数据类型，可选 XML, Json, jsonp, script, html, text。
                dataType: 'text',
                 
                //指明客户端要向哪个页面里面的哪个方法发送请求
                 url: "${basePath}/user/getUserInfo.action",
                 
                 data:{
/*                 	 action:"ulogin",
                     username:$("#username").val(),
                      userpass:$("#userpass").val(),
                     time: new Date() */
                 },
 
                //客户端调用服务器端方法成功后执行的回调函数
                 success: function(data) {
                    $('#username').html(data);
                 }
             })
        }
    })
    ///////////////////////////////////////////////////////////////////////
</script>
<body>
	<br>
	<c:if test="${not empty isLogin}">
		hello <span id="username"></span>
		<a href="${basePath}/user/logout.action">退出</a><br>
		<img src="${basePath}/img/shanzhi.png">
	</c:if>
	<c:if test="${empty isLogin}">
		<a href="${basePath}/forward/toLogin.action">登录</a>
	</c:if>
	<!-- <input type="button" id="btnGetUserInfo" value="用户信息查询";/> -->
</body>
</html>