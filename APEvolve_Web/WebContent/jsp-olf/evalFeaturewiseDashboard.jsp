<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Expires","0");
response.setDateHeader("Expires",-1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>APIfication | Score</title>
<script type="text/javascript" src="/APEvolve/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/APEvolve/js/apievaluation-main.js"></script>
</head>
<body>
<c:if test="${sessionScope.loginStatus != 'success'}">
	<c:redirect url="/login"/>
</c:if>
Welcome <c:out value="${sessionScope.compName}" />
<br><br>

<h4>Details of category: ${catMast[selectedCat]} </h4>

<table border="1">
<c:forEach items="${requestScope.scoreMapObj}" var="prodMap">
<tr>	
	<th> ${prodMap.key}</th>
	<c:forEach items="${prodMap.value}" var="arrList">	
		<td>${arrList}&nbsp;</td>
    </c:forEach>    
</tr>
</c:forEach>
</table>
</body>
</html>