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
	if( document.sessionregistration.companyName.value == "" )
    {
       alert( "Please provide company name!" );
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

