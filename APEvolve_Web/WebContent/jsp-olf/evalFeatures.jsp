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
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>TCS Appification Portal for Product Evaluation</title>
    <link href="/APEvolve/css/bootstrap.min.css" rel="stylesheet">
    <link href="/APEvolve/css/font-awesome.min.css" rel="stylesheet">
    <link href="/APEvolve/css/prettyPhoto.css" rel="stylesheet">
    <link href="/APEvolve/css/main.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/APEvolve/images/ico/apple-touch-icon-144-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="/APEvolve/images/ico/apple-touch-icon-114-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="/APEvolve/images/ico/apple-touch-icon-72-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" href="/APEvolve/images/ico/apple-touch-icon-57-precomposed.pn">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>APIfication | Feature selection</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="/APEvolve/js/navAccordion.js"></script>	
<script type="text/javascript" src="/APEvolve/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/APEvolve/js/apievaluation-main.js"></script>
</head>
<body data-spy="scroll" data-target="#navbar" data-offset="0">

    <header id="header" role="banner">
        <div class="container">
            <div id="navbar" class="navbar navbar-default">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.html"></a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Product Evaluation</a></li>                        
                        <li><a href="logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header><!--/#header-->

<div class="container">
<c:if test="${sessionScope.loginStatus != 'success'}">
	<c:redirect url="/login"/>
</c:if>
Welcome <c:out value="${sessionScope.compName}" />
<br><br><br>

<h2>Selected Products:</h2>
<ul>
<c:forEach items="${selectedProd}" var="prodObj">
	<li>${prodObj.productName}</li>
</c:forEach>
</ul>
<div class="col-md-12">
<h2>Features:</h2>
	<div>
		<form action="evaluation" name="frmFeatureEval" method="post">
		<c:set var="temp1"  value=""/>
  	<c:set var="idxFlag" value="0" />
	<!-- Navigation -->
	<nav class="mainNav">
		<ul>
		<c:forEach items="${featuresListObj}" var="featuresObj">
		
		<c:set var="temp" value="${featuresObj.category}" />

		<c:if test="${temp ne temp1}">
			<c:if test="${idxFlag ne '0'}">
			</ul>
			</li>
			</c:if>
			<li class="maincategory"><a href="#">${featuresObj.category}</a>
			<ul>		
		</c:if>		
		
			<li>
			<div class="row">
			<div class="col-md-12">
			<div class="col-md-1 checkclass"><input type="checkbox" name="evalFeatures" class="featurecheckbox" value="${featuresObj.featureId}"/></div>
			<div class="col-md-10 featureclass">${featuresObj.features}</div>										
			<div class="col-md-1 selectclass"><select name="${featuresObj.featureId}" id="${featuresObj.featureId}" class="selectiontype">
			<option value="">-Select-</option>
			<option value="${requestScope.weightageVals['low']}">Low</option>
			<option value="${requestScope.weightageVals['medium']}">Medium</option>
			<option value="${requestScope.weightageVals['high']}">High</option>
			</select>
			</div>
			</div>
			</div>
			</li>										
		<c:set var="idxFlag" value="${idxFlag + 1}" />
		<c:set var="temp1" value="${temp}" />
		</c:forEach>
			
		</ul>
	</nav>
			<tr><td colspan="3" align="center">
			<input type="button" Value="Evaluate" onclick="return validateEvalFeatures();"></td></tr>
		
		</form>
	</div>
	<div id="outer-wrap">
  	

</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="/APEvolve/js/navAccordion.js"></script>
	<script>
		jQuery(document).ready(function(){
		
			//Accordion Nav
			jQuery('.mainNav').navAccordion({
				expandButtonText: '<i class="fa fa-plus"></i>',  //Text inside of buttons can be HTML
				collapseButtonText: '<i class="fa fa-minus"></i>'
			}, 
			function(){
				console.log('Callback')
			});
			
		});
	</script>
	
</body>
</html>