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
    <link href="/APEvolve/css/bootstrap.min.css" rel="stylesheet">
    <link href="/APEvolve/css/font-awesome.min.css" rel="stylesheet">
    <link href="/APEvolve/css/prettyPhoto.css" rel="stylesheet">
    <link href="/APEvolve/css/main.css" rel="stylesheet">
<script type="text/javascript" src="/APEvolve/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/APEvolve/js/apievaluation-main.js"></script>
</head>
<body>
<div align="center">
<c:if test="${sessionScope.loginStatus != 'success'}">
	<c:redirect url="/login"/>
</c:if>
Welcome <c:out value="${sessionScope.compName}" />
<br><br><br><br>

<c:set var="idxFlag" value="0" />
<table border="1">

<c:forEach items="${requestScope.scoreMapObj}" var="prodMap">
<tr>
	<c:choose>
		<c:when test="${idxFlag eq '0'}">
	    	<th>${prodMap.key}</th>
		</c:when>
		<c:when test="${prodMap.key eq 'Total'}">
	    	<th>${prodMap.key}</th>
		</c:when>
		<c:otherwise>		
	    	<th> <a href="#" onclick="showCategoryDetails('${prodMap.key}');">${catMast[prodMap.key]}</a></th>
		</c:otherwise>
	</c:choose>		    
	<c:forEach items="${prodMap.value}" var="arrList">	
		<td>${arrList}&nbsp;</td>
    </c:forEach>
    <c:set var="idxFlag" value="${idxFlag + 1}" />
</tr>
</c:forEach>  

</table>
<br>

<div>

<button class="button" onclick="generateCSVReport();">Download CSV</button> 
</div>
</div>
</body>
</html>