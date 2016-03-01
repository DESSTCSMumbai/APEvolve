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

<title>APEvolve - Sign Up</title>

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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript" src="/APEvolve/js/apievaluation-main.js"></script>

</head>

<body>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<a class="navbar-brand" href="/index"></a>
			</div>
			<!-- /.navbar-header -->

		</nav>
	</div>
<c:if test="${not empty signUpMsg}">
            <div class="alert alert-warning"> <strong> <c:out value="${signUpMsg}" /></strong>
	</div></c:if>
	<c:remove var="signUpMsg" />

	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="signup-panel panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Please Sign Up</h3>
					</div>
					<div class="panel-body">
						<form action="/APEvolve/signupcheck" method="post" name="signupForm">
							<fieldset>
								<div class="form-group">
									<div class="row">
										<div class="col-md-6">

											<input class="form-control" name="firstName" id="firstName" placeholder="First Name" required="required" autofocus>
										</div>
										
										<div class="col-md-6">
											<input class="form-control" name="lastName" id="lastName"  placeholder="Last Name" required="required">
										</div>
									</div>
								</div>
								<div class="form-group">

									<input type="email" class="form-control" placeholder="Email" id="email" name="email" type="text" required="required" >
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-md-6">
											<input class="form-control" type="password" placeholder="Password" name="password" id="password" required="required">
										</div>
										<div class="col-md-6">

											<input class="form-control" type="password" placeholder="Confirm- Password" name="confPassword" id="confPassword" required="required">

										</div>
									</div>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Phone Number" maxlength="10" pattern="[0-9]{10}" name="phone" id="phone" type="tel" required="required">
								</div>
								
								<div class="form-group">
									<input class="form-control"  data-validation="length" data-validation-length="3-50" placeholder="CompanyName" name="company" id="company" type="text" required="required" >
								</div>
								
								<div class="form-group">
									<select class="form-control" required="required" name="designation" >
									<option  disabled selected="selected" value="">Designation</option>
										<option value="Business Analyst">Business Analyst</option>
										<option value="Chief Technical Officer">Chief Technical Officer</option>
										<option value="Developer">Developer</option>
										<option value="Solution Architect">Solution Architect</option>
										<option value="Technical Architect">Technical Architect</option>
									</select>
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<button class="btn btn-lg btn-primary btn-block" onclick="return validateSignUp()">Sign Up</button>
								<br>
                                Already have an account? <a href="/APEvolve/index">Sign In</a>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="wrapper">
		<div class="navbar navbar-default navbar-fixed-bottom"
			role="navigation">


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
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.2.8/jquery.form-validator.min.js"></script>
	<script> $.validate(); </script>

</body>
</html>
