var pieChartData = [];
var prodLable =[];
var prodScore= [];
var CompExist=''; 
function addValuesForChart(prodName, prodValue){
	pieChartData.push({"label":prodName,"value":prodValue});
	//alert(pieChartData.length);
	/*for(i=0;i<pieChartData.length;i++){
	   alert("ID:- "  + employees[i].id + " Name:- "  + employees[i].name + " Age:- " + employees[i].age);
	  }*/
}
function addValuesForChartLabel(prodName){
	prodLable.push(prodName);	
}
function addValuesForChartVal(prodValue){
	prodScore.push(prodValue);	
}
//this will move selected items from source list to destination list      
function move_list_items(sourceid, destinationid) {
	$("#" + sourceid + "  option:selected").appendTo("#" + destinationid);
}

//this will move all selected items from source list to destination list
function move_list_items_all(sourceid, destinationid) {
	$("#" + sourceid + " option").appendTo("#" + destinationid);
}

function validateEvalRegisteration()
{ 
	//alert(CompExist);
	if( document.sessionregistration.companyName.value == "" )
    {
       alert( "Please provide company name!" );
       document.sessionregistration.companyName.focus() ;
       return false;
    }
	else if(CompExist=="Y") {
    	alert( "Please provide unique company name!" );
        document.sessionregistration.companyName.focus() ;
        return false;
    } 
	else if( document.sessionregistration.selectedproducts.options.length == 0)
    {
	       alert( "Please select products!" );
	       document.sessionregistration.selectedproducts.focus() ;
	       return false;
    }
	 
	else if( document.sessionregistration.highVal.value == "" )
    {
       alert( "Please provide value for high weightage!" );
       document.sessionregistration.highVal.focus() ;
       return false;
    }
	else if( document.sessionregistration.mediumVal.value == "" )
    {
       alert( "Please provide value for medium weightage!" );
       document.sessionregistration.mediumVal.focus() ;
       return false;
    }
	else if( document.sessionregistration.lowVal.value == "" )
    {
       alert( "Please provide value for low weightage!" );
       document.sessionregistration.lowVal.focus() ;
       return false;
    } 
	$('#selectedproducts option').prop('selected', true);
	document.sessionregistration.submit();
}


function validateEvalFeatures() 
{
	//var selCount = 0;
	var checkboxes = document.getElementsByName('evalFeatures');
	var isValid = false;
	var selCount = $("[type='checkbox']:checked").length;
	
	if(selCount==0){
		alert("Please select the features to evaluate!");
		isValid = false;
		return false;
	} 
	else {
		$('input:checkbox[name=evalFeatures]').each(function(){ 
			if($(this).is(':checked')) {
				var temp = $(this).val();				
				var selWeight = document.getElementById(temp);
								
				if(selWeight.value === ''){				
					alert("Please select your priority against selected feature!");
					//selWeight.focus();
					isValid = false;
					return false;
				}
				else 
					isValid = true;
			}				
		});		
	}
	if(isValid)
	document.frmFeatureEval.submit();
}

function showCategoryDetails(cat) {

	//var win = window.open("/APEvolve/DashboardController?src=rpt&category="+cat, '_blank');
	var win = window.open("/APEvolve/dashboard?src=rpt&category="+cat, '_blank');
	  win.focus();
}

function generateCSVReport(){
	///ReportController
	var win = window.open("/APEvolve/reports", '_blank');
	win.focus();
}

function drawDashboardChart(){
	var w = 300,                        //width
    h = 300,                            //height
    r = 150,                            //radius
    color = d3.scale.category20c();     //builtin range of colors
  /*   data = [{"label":"one", "value":20}, 
            {"label":"two", "value":50}, 
            {"label":"three", "value":30}]; */
            
	   for(var i=0; i<prodLable.length; i++)  {
		   pieChartData.push({label: prodLable[i], value: prodScore[i]});
	   }        
            
    data = pieChartData;
    var vis = d3.select("#chartDash")
        .append("svg:svg")              //create the SVG element inside the <body>
        .data([data])                   //associate our data with the document
            .attr("width", w)           //set the width and height of our visualization (these will be attributes of the <svg> tag
            .attr("height", h)
        .append("svg:g")                //make a group to hold our pie chart
            .attr("transform", "translate(" + r + "," + r + ")")    //move the center of the pie chart from 0, 0 to radius, radius
    var arc = d3.svg.arc()              //this will create <path> elements for us using arc data
        .outerRadius(r);
    var getAngle = function (d) {
        return (180 / Math.PI * (d.startAngle + d.endAngle) / 2 - 90);
    };
    var pie = d3.layout.pie()           //this will create arc data for us given a list of values
        .value(function(d) { return d.value; });    //we must tell it out to access the value of each element in our data array
    var arcs = vis.selectAll("g.slice")     //this selects all <g> elements with class slice (there aren't any yet)
        .data(pie)                          //associate the generated pie data (an array of arcs, each having startAngle, endAngle and value properties) 
        .enter()                            //this will create <g> elements for every "extra" data element that should be associated with a selection. The result is creating a <g> for every object in the data array
            .append("svg:g")                //create a group to hold each slice (we will have a <path> and a <text> element associated with each slice)
                .attr("class", "slice");    //allow us to style things in the slices (like text)
        arcs.append("svg:path")
                .attr("fill", function(d, i) { return color(i); } ) //set the color for each slice to be chosen from the color function defined above
                .attr("d", arc);                                    //this creates the actual SVG path using the associated data (pie) with the arc drawing function
        arcs.append("svg:text")                                     //add a label to each slice
                .attr("transform", function(d) {                    //set the label's origin to the center of the arc
                //we have to make sure to set these before calling arc.centroid
                d.innerRadius = 0;
                d.outerRadius = r;
                return "translate(" + arc.centroid(d) + ")";//  +
                //"rotate(" + getAngle(d) + ")"
                //;        //this gives us a pair of coordinates like [50, 50]
            })
            .attr("dy", 5)
            .attr("text-anchor", "middle")                         //center the text on it's origin
            .text(function(d, i) { return data[i].label+":"+data[i].value; });        //get the label from our original data array      
} 
function validateSignUp() 
{
	if( document.signupForm.firstName.value.trim() == "" )
    {
       alert( "Please provide first name!" );
       document.signupForm.firstName.focus() ;
       return false;
    }
	else if( document.signupForm.lastName.value.trim() == "" )
    {
	       alert( "Please provide last name!" );
	       document.signupForm.lastName.focus() ;
	       return false;
    }
	else if( document.signupForm.email.value.trim() == "" )
    {
	       alert( "Please provide email!" );
	       document.signupForm.email.focus() ;
	       return false;
    }
	else if (!validateEmail(document.signupForm.email.value)) {
		alert( "Please provide proper email!" );
	       document.signupForm.email.focus() ;
	       return false;
	}	
	else if( document.signupForm.password.value.trim() == "" )
    {
	       alert( "Please provide password!" );
	       document.signupForm.password.focus() ;
	       return false;
    }
	else if( document.signupForm.confPassword.value.trim() == "" )
    {
	       alert( "Please provide confirmation Password !" );
	       document.signupForm.confPassword.focus() ;
	       return false;
    }
	else if( document.signupForm.phone.value.trim() == "" || (isNaN(document.signupForm.phone.value)) || (document.signupForm.phone.value.length !=10))
    {
	       alert( "Please provide phone!" );
	       document.signupForm.phone.focus() ;
	       return false;
    }	
	else if( document.signupForm.company.value.trim() == "" )
    {
	       alert( "Please provide company!" );
	       document.signupForm.company.focus() ;
	       return false;
    }
	else if( document.signupForm.designation.value == "" )
    {
	       alert( "Please provide designation!" );
	       document.signupForm.designation.focus() ;
	       return false;
    }
	else if( document.signupForm.password.value != document.signupForm.confPassword.value )
    {
	       alert( "Password provided in both fields doeas not match!" );
	       document.signupForm.password.focus() ;
	       return false;
    }
	document.signupForm.submit();
}


function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}


function checkComapnyName()
{
	
	var compName = $('#companyName').val();
	if(compName.trim()=='') {
		alert("Please provide company name!.");
		document.sessionregistration.companyName.focus();
		return false;
	}
    $.ajax({
        type: "POST",
        url: "/APEvolve/AjaxCheck", /* The request will be processed in this file.. */
        beforeSend: function() {
            $("#compStatus").html("geting name...");
        },
        data: "compname="+compName,
        success: function(msg) {
        	
        	CompExist=msg.substring(0,1);   
        	
            $("#compStatus").html(msg.substring(1));
        }
    });
}