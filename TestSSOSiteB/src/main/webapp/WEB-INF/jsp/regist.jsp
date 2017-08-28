<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%-- <%@include file="/view/resource.jsp" %> --%>
  </head>
  <body>
  	<%@include file="/WEB-INF/jsp/header.jsp"%>
  	<div> 
     	<!-- 注册form -->
     	<form id="registForm" action="${basePath}/user/regist.action" method="post">   
     		<input type="text" name="username">
     		<input type="text" name="password">
     		<input type="submit" value="注册">
     	</form>
     	<br>
     	<span>${result}</span>
  	 </div> 
  </body>
</html>
