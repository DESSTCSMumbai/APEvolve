<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Expires","0");
response.setDateHeader("Expires",-1);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>TCS Appification Portal for Product Evaluation</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/prettyPhoto.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.pn">
</head><!--/head-->

<body data-spy="scroll" data-target="#navbar" data-offset="0">
    <header id="header" role="banner">
        <div class="container-fluid">
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
                
                        <li><a href="#login">Login</a></li>
                        <li><a href="/APEvolve/jsp/contact.jsp">Contact</a></li>

                </div>
            </div>
        </div>
    </header><!--/#header-->

    <section id="main-slider" class="carousel">
        <div class="carousel-inner">
            <div class="item active">
                <div class="container">
                    <div class="carousel-content">
                         <h1></h1>
                        <p class="lead"><br></p>
                    </div>
                </div>
            </div><!--/.item-->
            <div class="item">
                <div class="container">
                    <div class="carousel-content">
                        <h1>							</h1>
                        <p class="lead">				</p>
                    </div>
                </div>
            </div><!--/.item-->
        </div><!--/.carousel-inner-->
      
    </section><!--/#main-slider-->

    <div class="container">
            <div class="box first">

  <c:if test="${not empty sessionScope.loginMsg}">
            <div class="alert alert-warning">           <strong>Warning!</strong> <c:out value="${loginMsg}" />
	</div></c:if>
	<c:remove var="loginMsg" scope="session" />

                 <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="center">
      <form action="/APEvolve/loginCheck" method="post" name="loginForm">       
      <h4 class="form-signin-heading">Please Login</h4>
      
      <input type="text" class="form-control" name="login-username" placeholder="Username" required="" autofocus="" /><br/>
      <input type="password" class="form-control" name="login-password" placeholder="Password" required="" />    
      <br/>      
      <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
    </form>

	
                        </div>
                    </div><!--/.col-md-4-->
				  </div><!--/.row-->
				
                
            </div><!--/.box-->
        </div><!--/.container-->

    <footer id="footer">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    &copy; 2014 <a target="_blank" href="http://www.tcs.com/" title="Tata Consultancy Services">Tata Consultancy Services</a>. All Rights Reserved.
                </div>
                <div class="col-sm-6">

                </div>
            </div>
        </div>
    </footer><!--/#footer-->

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.isotope.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/main.js"></script>
</body>
</html>