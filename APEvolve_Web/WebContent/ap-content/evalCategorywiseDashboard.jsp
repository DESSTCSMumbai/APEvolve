<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Expires", "0");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>APEvolve - Dashboard</title>

<!-- Bootstrap Core CSS -->
<link
	href="/APEvolve/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="/APEvolve/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/APEvolve/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="/APEvolve/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script type="text/javascript" src="/APEvolve/js/apievaluation-main.js"></script>

<!-- D3 charts -->
<script type="text/javascript" src="http://mbostock.github.com/d3/d3.js?2.1.3"></script>
<!-- <script type="text/javascript" src="/APEvolve/js/d3.v2.js"></script> -->
 <style type="text/css">
     .slice text {
         font-size: 10pt;
         font-family: Arial;
         color: white;
     }   
 </style>    
  <!-- D3 charts -->  
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<c:if test="${sessionScope.loginStatus != 'success'}">
		<c:redirect url="/index" />
	</c:if>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"></a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">

				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"><i class="fa fa-user fa-fw"></i><c:out value="${sessionScope.compName}" /></a></li>

						<li class="divider"></li>
						<li><a href="/APEvolve/logout"><i
								class="fa fa-sign-out fa-fw"></i> Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			
		</nav>
		
		<div class="page-wrapper selectionpage">
		<br>
		<div class="row">
                <div class="col-lg-12">
                
                <div class="panel panel-primary">
                        <div class="panel-heading">
                            Category Wise Dashboard
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                
                
<c:set var="idxFlag" value="0" />
<table class="table table-striped table-bordered table-hover">

<c:forEach items="${requestScope.scoreMapObj}" var="prodMap">

	<c:choose>
		<c:when test="${idxFlag eq '0'}">
		</tr></thead>
	    	<thead><tr><th>${prodMap.key}</th>	    	
		</c:when>
		<c:when test="${prodMap.key eq 'Total'}">
		</tr></tbody>
	    	 <tbody><tr><th>${prodMap.key}</th>
		</c:when>
		<c:otherwise>
		</tr></tbody>
	    	<tbody><tr><td><a href="#" onclick="showCategoryDetails('${prodMap.key}');">${catMast[prodMap.key]}</a></td>
		</c:otherwise>
	</c:choose>		    
	<c:forEach items="${prodMap.value}" var="arrList">
	<c:if test="${idxFlag eq '0'}"><th>${arrList}</th><script>addValuesForChartLabel('${arrList}');</script></c:if>
	
	<c:if test="${idxFlag ne '0'}">
		<c:if test="${prodMap.key eq 'Total'}">
		<!-- <script>addValuesForChart('${idxFlag}','${arrList}');</script> -->
		<script>addValuesForChartVal('${fn:substringBefore(arrList, "/")}');</script>
		</c:if>
		<td>${arrList}&nbsp;</td>	
		
	</c:if>
		
    </c:forEach>
    <c:set var="idxFlag" value="${idxFlag + 1}" />

</c:forEach>  

</table>
<c:if test="${sessionScope.loginUserName eq 'admin'}">
	<button class="btn btn-success registerbutton" onclick="generateCSVReport();">Download CSV</button> 
</c:if>
                </div>
                <!-- /.col-lg-12 -->
           
        
</div>
		<!-- /#page-wrapper -->
		
		<div class="panel panel-primary">
                        <div class="panel-heading">
                            Dashboard
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                        <div class="row">
                        <div class="col-md-12">
		 <div id="chartDash" align="center" ><script>drawDashboardChart();</script></div>
		 </div>
		 </div>
		
    </div></div>
                            
        </div>                 </div>    
</div>
	</div>
	
	
	
	
	<div id="wrapper">
     <div class="navbar navbar-default navbar-fixed-bottom" role="navigation">
      
	  
	  <a class="navbar-brand-footer" href="#"></a> 
	  </div> 
  </div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="/APEvolve/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="/APEvolve/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="/APEvolve/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="/APEvolve/dist/js/sb-admin-2.js"></script>


</body>

</html>
