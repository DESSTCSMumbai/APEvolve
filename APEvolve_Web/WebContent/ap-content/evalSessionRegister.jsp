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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>APEvolve - Session Registration </title>

    <!-- Bootstrap Core CSS -->
    <link href="/APEvolve/bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/APEvolve/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/APEvolve/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/APEvolve/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
	<c:redirect url="/index"/>
</c:if>
    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"></a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
        
			<li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i>Welcome ${sessionScope.loginUserName}</a>
                        </li>
                        
                        <li class="divider"></li>
                        <li><a href="/APEvolve/logout"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->


            <!-- /.navbar-static-side -->
        </nav>

        <div class="page-wrapper selectionpage">
      
            <div class="row"> 
                <div class="col-md-12">
                    <!-- <h4 class="page-header">Product Selection</h4> -->
                    <br> 
                    <!-- <h4>Product Selection</h4> -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            Evaluation Session Registration
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form name="sessionregistration" method="post" >
                                    <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label>Company Name</label>
                                            <input id="companyName" type="text" name="companyName" class="form-control" onblur="return checkComapnyName();" />
                                              <span id="compStatus" class="compStatus"></span>
                                            
                                           
                                        </div>
                                        </div>
                                      
                                        </div>
                                        <div class="row">
                                        <div class="form-group col-lg-4">
                                            <label>High</label>
                                            <input class="form-control" type="number" value="5" name="highVal" id="highVal" min="1" max="5">
                                        </div>
                                                                                <div class="form-group col-lg-4">
                                            <label>Medium</label>
                                            <input class="form-control" type="number" name="mediumVal" id="mediumVal" min="1" max="5" value="3" >
                                        </div> 
                                                                                <div class="form-group col-lg-4">
                                            <label>Low</label>
                                            <input class="form-control"  type="number" name="lowVal" id="lowVal" value="1" min="1" max="5">
                                        </div>
                                       </div>
                                      
                                        <div class="row">
                                        <div class="form-group col-lg-4">
                                            <label>Product List</label>
                                            <select multiple class="form-control" id="mainProductsList" data-style="btn-info" name="mainProductsList" style="height: 120px;">
                                                <c:forEach items="${productMasterList}" var="productMasterList">					 
												<option value="${productMasterList.productId}">${productMasterList.productName}</option>				
												</c:forEach>
                                            </select>
                                        </div>
                                                                                <div class="form-group col-lg-4" align="center">
                                            <label>Navigate</label><br>
                                            <button type="button" class="btn  btn-sm navigationbuttons" id="moveright" onclick="move_list_items('mainProductsList','selectedproducts');">></button><br>
                                        	<button type="button" class="btn btn-sm navigationbuttons" id="moverightall" onclick="move_list_items_all('mainProductsList','selectedproducts');" >>></button><br>
                                        	<button type="button" class="btn  btn-sm navigationbuttons" id="moveleft" onclick="move_list_items('selectedproducts','mainProductsList');" ><</button><br>
                                        	<button type="button" class="btn btn-sm navigationbuttons" id="moveleftall" onclick="move_list_items_all('selectedproducts','mainProductsList');" ><<</button><br>
                                            
                                        </div>
                                        
                                                                                <div class="form-group col-lg-4">
                                            <label>Selected Products</label>
                                            
                                            <select multiple="multiple" id="selectedproducts" name="selectedproducts" style="height: 120px;" class="form-control">
      										</select>  
                                            
                                        </div>                                        
                                        </div>
                                        <button type="button" class="btn btn-success registerbutton" onclick="return validateEvalRegisteration()">Register</button>
                                         
                                    </form>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                       
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
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
    <script src="/APEvolve/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="/APEvolve/bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="/APEvolve/dist/js/sb-admin-2.js"></script>

</body>

</html>
