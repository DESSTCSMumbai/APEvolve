myApp
		.controller(
				'SubmitController',
				[
						'$scope',
						'$http',
						'APEvolveAPI',
						'$rootScope',
						'$location',
						'$routeParams',
						function($scope, $http, APEvolveAPI, $rootScope, $location,
								$routeParams) {
							// reset login status
							var param1 = $routeParams.producttype;

							$scope.save = function() {
								console.log('what i m doing here test1');
								// var evalid=EvalId;
								console.log($scope.EvalId);
								console.log($scope.selectedproduct);
								console.log($scope.radioption);
								console.log($scope.myVar);
								if($scope.radioption=="yes")
									$rootScope.domain=$scope.myVar;
								var parameter = JSON.stringify({
									"companyName" : $scope.EvalId,
									"selectedProducts" : $scope.selectedproduct
								});
								console.log(parameter);
								$http
										.post(
												APEvolveAPI + '/APEvolveRest2/rest/session/sessionregister',
												parameter)
										.success(function(respon) {
											$scope.status = respon;
											console.log(respon);
											if (respon.regSaveCode == 'S') {
												$location.path('/featureEval');
												console.log($location.path());
											}
										});
							};

							$scope.productanddomain = function() {
								console.log('what i m doing here test1');
								var parameter = JSON.stringify({});
								console.log(param1);
								$http
										.get(
												APEvolveAPI + '/APEvolveRest2/rest/evaluation/productsanddomains/'
														+ param1, parameter)
										.success(
												function(respon) {
													$rootScope.detaileval = respon;
													$scope.availableproducts = respon;
													console
															.log($rootScope.detaileval);
													$scope.availableproducts = respon.products;
													console
															.log($scope.products);
													$scope.domains = respon.domains;
													console.log($scope.domains);
												});

							};

							$scope.checkCompany = function(evalid) {
								console.log('what i m doing here test1');
								var parameter = JSON.stringify({});
								console.log(parameter);
								$http.get(
										APEvolveAPI + '/APEvolveRest2/rest/session/checkCompany/'
												+ evalid, parameter).success(
										function(respon) {
											$scope.response = respon;
											console.log($scope.response);
										});

							};
							$scope.selectedproducts = [];
							$scope.selectedproduct = [];
							$scope.moveItem = function(item, from, to) {
								console.log('Move item   Item: ' + item
										+ ' From:: ' + from + ' To:: ' + to);
								// Here from is returned as blank and to as
								// undefined

								var idx = from.indexOf(item);
								if (idx != -1) {
									from.splice(idx, 1);
									to.push(item);
									$scope.selectedproduct.splice(0, 0,
											item.productId);
								}
							};
							
							$scope.featureEval = function() {
								console.log('what i m doing here line 98 submitcontrollers');
								var parameter = JSON.stringify({});
								console.log($rootScope.domain);
								if($rootScope.domain!=null)
									{
								$http.get(
										APEvolveAPI + '/APEvolveRest2/rest/evaluation/evalfeatures/'
												+$rootScope.domain, parameter).success(
										function(respon) {
											$scope.response = respon;
											console.log($scope.response+"response value is");
											
											
											
										});}
								else
								$http.get(
										APEvolveAPI + '/APEvolveRest2/rest/evaluation/evalfeatures', parameter).success(
										function(respon) {
											$scope.response = respon;
											console.log($scope.response);
											
										
										});

							};
							
							
							
							
							$scope.detailfeature = function () {
							    console.log(1);
							    var detaileval;
							console.log('what i m doing here test1');
							var parameter=JSON.stringify({compName:cname});
							console.log(parameter);
							$http.post(APEvolveAPI + '/APEvolveRest2/rest/evaluation/dashboard', parameter)
							.success(function (respon) {
							$rootScope.detaileval=respon;
							console.log($scope.detaileval);
							$location.path('/details');
							});
							     
							};	
							
							
							
							
							
							
						} ]);