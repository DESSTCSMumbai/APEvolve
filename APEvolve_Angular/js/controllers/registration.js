myApp.controller('LoginController',
 ['$scope','APEvolveAPI','$rootScope', '$location', 'AuthenticationService',
    function ($scope,APEvolveAPI, $rootScope, $location, AuthenticationService) {
        // reset login status
        
            
        $scope.login = function () {
            $scope.dataLoading = true;
            $rootScope.user=false;
      console.log('what i m doing here');
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
              console.log("where i m");

      console.log(response.user);
                if(response.succes) {
                        console.log("where i m");
                  console.log(response.role);
                    AuthenticationService.SetCredentials($scope.username, $scope.password,response.role);          
                    $location.path('/home');
            
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
            $rootScope.user=true; 
        };
    }]);

myApp.controller('RegistrationController',
 ['$scope','APEvolveAPI' ,'$rootScope', '$location', 'AuthenticationService',
    function ($scope,APEvolveAPI, $rootScope, $location, AuthenticationService) {
        // reset login status
       console.log("i m in register");
  $rootScope.bodyClass = 'body-one';
        $scope.Register = function () {
            $scope.dataLoading = true;
      console.log('Registration started');
            AuthenticationService.Registration($scope.FirstName,
$scope.LastName , $scope.Email, $scope.Password, $scope.Phone , $scope.Company, $scope.Designation, 
function(response) {
              console.log("where i m");
      console.log(response.user);
                if(response.succes) {
                    $rootScope.SignUpStatus="Registration Successful";

                    $location.path('/home');
                    console.log("get forward");
                } else {
                    $scope.error = response.message;
                    alert('Email already Exist');
                    $scope.dataLoading = false;
                }
            });
        };
    }]);

myApp.controller('LogoutController',
 ['$scope','APEvolveAPI', '$rootScope', '$location', 'AuthenticationService',
    function ($scope, APEvolveAPI,$rootScope, $location, AuthenticationService) {
        // reset login status
        AuthenticationService.ClearCredentials();
   console.log("cleared SetCredentials");
      $location.path('/login');
    }]);

myApp.controller('AdminController',
		 ['$scope','APEvolveAPI','$http', '$rootScope', '$location',
		    function ($scope,APEvolveAPI, $http, $rootScope, $location) {

		        // reset login status
		        console.log("in now forever");
		    
		        console.log($rootScope.globals.currentUser);        
		 
		      console.log("in now forever");
		     $http.get(APEvolveAPI + '/APEvolveRest2/rest/masterdata/registeredusers')
		                .success(function (respon) {
		          $scope.registeredUser=respon;
		          console.log($scope.registeredUser);
		    
		});

		$scope.fullDetails=function(element) {
		 $scope.dataLoading = true;
		 var NAME = element;
		    var currentClass = NAME.className;
		    console.log(currentClass);

		};
		  
		 $scope.accept = function (user) {
	            $scope.dataLoading = true;
	      console.log('what i m doing here');
	      var parameter = JSON.stringify({firstName:user.firstName,lastName:user.lastName,email:user.email,signupDate:user.signupDate,phone:user.phone,designation:user.designation,company:user.company});
	      $http.post(APEvolveAPI + '/APEvolveRest2/rest/masterdata/approveregistereduser',parameter).success(function (respon) 
	    		  {
	          $scope.status=respon;
	          alert(status);
	          console.log($scope.status);
	    		  });
	        };
		    }]);

myApp.controller('ChartCtrl', ['$scope', function ($scope) {
  console.log("i m in chart");
    $scope.line = {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        data:   [
                    [65, 59, 80, 81, 56, 55, 40],
                    [28, 48, 40, 19, 86, 27, 90]
                ],
              
        colours:['#3CA2E0','#F0AD4E','#7AB67B','#D9534F','#3faae3'],
        onClick: function () {
          alert("i m in alert ");

        }
        
    };
    console.log("i m in chart2");
}]);
