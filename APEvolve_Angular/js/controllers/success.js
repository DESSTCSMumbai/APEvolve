myApp.controller('SuccessController',
	['$scope','$rootScope','AuthenticationService', function($scope, $rootScope, AuthenticationService) {
  
  console.log($rootScope.globals.currentUser.username);
  
}]);