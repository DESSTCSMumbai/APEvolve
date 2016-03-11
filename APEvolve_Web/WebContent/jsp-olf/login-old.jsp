<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
 response.setHeader("Cache-Control","no-cache"); //HTTP 1.1 
 response.setHeader("Pragma","no-cache"); //HTTP 1.0 
 response.setDateHeader ("Expires", 0); //prevents caching at the proxy server  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>APIfication | Login</title>
</head>
<body>
	<h3>LOGIN</h3>
	<form action="../LoginController" method="post" name="loginForm">
		Username: <input type="text" name="login-username"/><br/>
		Password: <input type="password" name="login-password"/><br/>
        <input type="submit" value="Login" />
	</form>
	<font color="red">
	<c:if test="${not empty sessionScope.loginMsg}">
            <c:out value="${loginMsg}" />
	</c:if>	
	</font>
	<c:remove var="loginMsg" scope="session" />
</body>
</html>