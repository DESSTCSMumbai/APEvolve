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
<html>
<head>

<title>APIFICATION | Evaluation Registeration</title>
<script type="text/javascript" src="/APEvolve/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/APEvolve/js/apievaluation-main.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>TCS Appification Portal for Product Evaluation</title>
    <link href="/APEvolve/css/bootstrap.min.css" rel="stylesheet">
    <link href="/APEvolve/css/font-awesome.min.css" rel="stylesheet">
    <link href="/APEvolve/css/prettyPhoto.css" rel="stylesheet">
    <link href="/APEvolve/css/main.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="/APEvolve/images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/APEvolve/images/ico/apple-touch-icon-144-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="/APEvolve/images/ico/apple-touch-icon-114-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="/APEvolve/images/ico/apple-touch-icon-72-precomposed.pn">
    <link rel="apple-touch-icon-precomposed" href="/APEvolve/images/ico/apple-touch-icon-57-precomposed.pn">
</head><!--/head-->
<c:if test="${sessionScope.loginStatus != 'success'}">
	<c:redirect url="/login"/>
</c:if>
Welcome 
<br><br>
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
                    <a class="navbar-brand" href="/APEvolve"></a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                                                                
                        <li><a href="#contact">Contact</a></li>
                        <li><a href="logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header><!--/#header-->
<div class="container">
<div class="col-md-10 col-md-offset-1 maincontent">


<form name="sessionregistration" method="post" action="registration" class="smart-green">
    <h1>Product Selection
        <span>Please fill all the texts in the fields.</span>
    </h1>
    <label>
        <span>Company Name :</span>
        <input id="companyName" type="text" name="companyName" />
    </label>
    
    <label>
    <div class="row">
    <div class="col-md-3">
        <span>High :</span>
        <input type="number" name="highVal" id="highVal" min="1" max="5">
        </div>
        <div class="col-md-3">
        <span>Medium :</span>
       <input type="number" name="mediumVal" id="mediumVal" min="1" max="5">
        </div>
        <div class="col-md-3">
        <span>Low :</span>
        <input type="number" name="lowVal" id="lowVal" min="1" max="5">
        </div>
    </div>
    </label>
    
    
    <label>
       <div class="row">
    <div class="col-md-3">
        <span>Products List</span>
        <select multiple="multiple" class="productlist" id="mainProductsList" data-style="btn-info" name="mainProductsList" style="height: 150px;; width: 150px">
		<c:forEach items="${productMasterList}" var="productMasterList">					 
		<option value="${productMasterList.productId}">${productMasterList.productName}</option>				
		</c:forEach>
		</select>
        </div>
        <div class="col-md-3 dragbuttons">
        <span></span>
        <input id="moveright" class="btn btn-success" type="button" value=">" onclick="move_list_items('mainProductsList','selectedproducts');" /><br/>
<input id="moverightall" class="btn btn-success" type="button" value=">>" onclick="move_list_items_all('mainProductsList','selectedproducts');" /><br/>
<input id="moveleft" class="btn btn-success" type="button" value="<" onclick="move_list_items('selectedproducts','mainProductsList');" /><br/>
<input id="moveleftall" class="btn btn-success" type="button" value="<<" onclick="move_list_items_all('selectedproducts','mainProductsList');" />
        </div>
        <div class="col-md-3">
        <span>Selected products:</span>
       <div class="col-md-12">    <select multiple="multiple" id="selectedproducts" name="selectedproducts" style="height: 150px; width: 150px;">
      </select>  </div>
    </div>
      </div>
    </label> 
    <label>
    <input type="button" class="button" id="formsubmit" value="test" >
    <input type="button" class="button" id="formsubmit" value="test" >
        <span>&nbsp;</span> 
        <input type="button" class="button" id="formsubmit" value="Submit" onclick="return validateEvalRegisteration()"> 
        
    </label>    
</form>

</div>
</div>


    
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

    <script src="/APEvolve/js/jquery.js"></script>
    <script src="/APEvolve/js/bootstrap.min.js"></script>
    <script src="/APEvolve/js/jquery.isotope.min.js"></script>
    <script src="/APEvolve/js/jquery.prettyPhoto.js"></script>
    <script src="/APEvolve/js/main.js"></script>
</body>
</html>



