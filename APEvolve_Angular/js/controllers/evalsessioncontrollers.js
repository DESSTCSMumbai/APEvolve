myApp.controller('AllevalsessionController',
 ['$scope','APEvolveAPI', '$http', '$rootScope', '$location',
    function ($scope,APEvolveAPI, $http, $rootScope, $location) {

        // reset login status
        console.log("in now forever");
    
               console.log($rootScope.globals.currentUser);        
      var api= "API";
      var evalSession;
console.log("in now forever");  
     var parameter=JSON.stringify({productType:api,role:$rootScope.globals.currentUser.role,username:$rootScope.globals.currentUser.username});
     console.log(parameter);
     $http.post(APEvolveAPI + '/APEvolveRest2/rest/evaluation/history', parameter)
                .success(function (respon) {
          $scope.evalSession=respon.evalHistory;
          console.log($scope.evalSession);
    
});

$scope.fullDetails=function(element) {
 $scope.dataLoading = true;
 var NAME = element;
    var currentClass = NAME.className;
    console.log(currentClass);

};

$scope.details = function (cname) {
     console.log(cname);
     $rootScope.sessionid=cname;
     var detaileval;
console.log('what i m doing here test1');
var parameter=JSON.stringify({compName:cname});
console.log(parameter);
$http.post(APEvolveAPI + '/APEvolveRest2/rest/evaluation/dashboard', parameter)
.success(function (respon) {
$rootScope.detaileval=respon;
console.log($scope.detaileval);

$location.path('/home/allevalsession/details');
});
      
};

$scope.detailfeature = function (category) {
    console.log(category.categoryId);
    console.log($rootScope.sessionid);
    var detaileval;
console.log('what i m doing here test1');
var parameter=JSON.stringify({compName:$scope.sessionid,catId:category.categoryId});
console.log(parameter);
$http.post(APEvolveAPI + '/APEvolveRest2/rest/evaluation/catdashboard', parameter)
.success(function (respon) {
$rootScope.featuredetails=respon;
console.log($rootScope.featuredetails);
$location.path('/home/allevalsession/details/detailfeature');
});
     
};

    }]);









