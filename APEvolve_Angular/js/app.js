var myApp = angular.module('myApp',
  ['ngRoute', 'ngCookies','angular.filter','chart.js']);

myApp.constant('APEvolveAPI','http://localhost:8083');

myApp.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/login', {
      templateUrl: 'views/login.html',
      controller: 'LoginController'
    }).
    when('/home/allevalsession', {
      templateUrl: 'views/allevalsession.html',
      controller: 'AllevalsessionController'
    }).
    when('/home/allevalsession/details', {
      templateUrl: 'views/evalsession.html',
      controller: 'AllevalsessionController'
    }).
    when('/logout', {
      templateUrl: 'views/login.html',
      controller: 'LogoutController'
    }).
    when('/register', {
      templateUrl: 'views/register.html',
      controller: 'RegistrationController'
    }).
    when('/home', {
      templateUrl: 'views/success.html'
    }).
    when('/home/userapproval', {
        templateUrl: 'views/userapproval.html',
        controller: 'AdminController'
      }).
      when('/home/userapproval/accept', {
          templateUrl: 'views/userapproval.html',
          controller: 'AdminController'
        }).
        when('/NESEval', {
            templateUrl: 'views/NewFile.html',  
          }).
          when('/NESEval/:producttype', {
              templateUrl: 'views/NewEvaluation.html',  
              controller: 'SubmitController'
            }).
          when('/newevalsession/getevalname', {
              templateUrl: 'views/NewEvaluation.html',
              controller: 'SubmitController'
            }).
            when('/featureEval', {
                templateUrl: 'views/FeatureEval.html',
                controller: 'SubmitController'
              }).
              when('/home/addproduct', {
                  templateUrl: 'views/AddProduct.html',
                  controller: 'addController'
                }).
                when('/home/addcategory', {
                    templateUrl: 'views/AddCategory.html',
                    controller: 'addController'
                  }).
                  when('/home/addfeature', {
                      templateUrl: 'views/AddFeature.html',
                      controller: 'addController'
                    }).
                      when('/details/detailfeature', {
                          templateUrl: 'views/featuresession.html',
                          controller: 'AllevalsessionController'
                        }).
                        when('/detailfeature', {
                            templateUrl: 'views/featuresession.html',
                          }).
    otherwise({
      redirectTo: '/login'
    });
}]);

myApp.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
          // if ($location.path() == '/logout' && $rootScope.globals.currentUser) {
            //    $location.path('/logout');
           // }
           if ($location.path() === '/logout' && $rootScope.globals.currentUser) {
                $location.path('/logout');
            }
           
            else if($location.path() == '/register' && !$rootScope.globals.currentUser) {   
                $location.path('/register');
            }
            else if ($location.path() === '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
            else if (!$rootScope.globals.currentUser) {
                $location.path('/login');
            }

        });
    }]);