<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>APEvolve - Features</title>

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
                            Evaluate features
                        </div>
                        <!-- .panel-heading -->
                        <div class="panel-body">
                            <div class="panel-group" id="accordion">
                            <form action="evaluation" name="frmFeatureEval" method="post">
							<c:set var="temp1"  value=""/>
								<c:set var="idxFlag" value="0" />
								<c:forEach items="${featuresListObj}" var="featuresObj">

									<c:set var="temp" value="${featuresObj.category}" />

									<c:if test="${temp ne temp1}">
										
											<c:if test="${idxFlag ne '0'}">
											</div>
										</div>
									</c:if>
										<div class="panel panel-info">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion" href="#collapse${idxFlag}">${featuresObj.category}</a>
												</h4>
											</div>
											<div id="collapse${idxFlag}" class="panel-collapse collapse">
									</c:if>

									
										<div class="panel-body">
											<div class="row">
												<div class="col-md-12">
													<div class="col-md-1 checkclass">
														<input type="checkbox" name="evalFeatures"
															class="featurecheckbox" value="${featuresObj.featureId}" />
													</div>
													<div class="col-md-10 featureclass">${featuresObj.features}</div>
													<div class="col-md-1 selectclass">
														<select name="${featuresObj.featureId}"
															id="${featuresObj.featureId}" class="selectiontype" >
															<option value="">-Select-</option>
															<option value="${requestScope.weightageVals['low']}">Low</option>
															<option value="${requestScope.weightageVals['medium']}">Medium</option>
															<option value="${requestScope.weightageVals['high']}">High</option>
														</select>
													</div>
												</div>
											</div>
										</div>
									

									<c:set var="idxFlag" value="${idxFlag + 1}" />
									<c:set var="temp1" value="${temp}" />
								</c:forEach>
								
								
								
							
                            </div>
                            
                        </div>
                        <!-- .panel-body -->
                        <button type="button" class="btn btn-success registerbutton" onclick="return validateEvalFeatures();">Evaluate</button>
                        </form>
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
</div>

		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
	<div id="wrapper">
     <div class="navbar navbar-default navbar-fixed-bottom" role="navigation">
      
	  
	  <a class="navbar-brand-footer" href="#"></a> 
	  </div> 
  </div>

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
