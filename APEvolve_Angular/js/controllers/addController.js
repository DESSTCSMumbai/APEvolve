myApp.controller('addController',
	['$scope','APEvolveAPI','$http','$rootScope','$route', function($scope,APEvolveAPI,$http,$rootScope,$route) {
  
		$scope.addproduct = function() {
			console.log('what i m doing here test1');
			var parameter = JSON.stringify({"productName" :$scope.productname ,"productVersion":$scope.versionno,"productType":$scope.producttype});
			console.log(parameter);
			$http
					.post(
							APEvolveAPI + '/APEvolveRest2/rest/masterdata/addproduct',
							parameter).success(function(respon) {
						$scope.status = respon;
						console.log(respon);
						if(respon.prodSaveCode=='F')
							{
							$scope.message="Product not added";
							}
						$route.reload();
					});
		};

		
		$scope.addcategory = function() {
			console.log('what i m doing here test1');
			var parameter = JSON.stringify({"categoryName" :$scope.categoryname,"prodType":$scope.producttype});
			console.log(parameter);
			$http
					.post(
							APEvolveAPI + '/APEvolveRest2/rest/masterdata/addcategory',
							parameter).success(function(respon) {
						$scope.status = respon;
						console.log(respon);
						if(respon.catSaveCode=='F')
							{
							$scope.message=respon.message;
							}
						$route.reload();
					});
		};
		
  
		$scope.addfeature = function() {
			console.log('what i m doing here test1');
			var parameter = JSON.stringify({"mstCategoryId": $scope.category,
				"feature": $scope.feature,
				"mstProductId": $scope.product,
				"rating": $scope.rating,
				"comment": $scope.comments,
				"reference": $scope.reference,
				"relevance": $scope.relevance});
			console.log(parameter);
			$http
					.post(
							APEvolveAPI + '/APEvolveRest2/rest/masterdata/addfeature',
							parameter).success(function(respon) {
						$scope.status = respon;
						console.log(respon);
						if(respon.catSaveCode=='F')
							{
							$scope.message=respon.message;
							}
						$route.reload();
					});
		};
		
		$scope.getprod_cat = function(type) {
			console.log('what i m doing here test1');
			var parameter = JSON.stringify({});
			console.log(parameter);
			$http
					.get(
							APEvolveAPI + '/APEvolveRest2/rest/evaluation/productsandcategories/'+type,
							parameter).success(function(respon) {
						$scope.products = respon.products;
						$scope.categorys = respon.categories;
						console.log($scope.products);
						console.log($scope.categorys);
					});
		};
  
}]);