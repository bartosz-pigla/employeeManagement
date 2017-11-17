var pizzaShopManagementApp = angular.module('employeeManagementApp',['ngRoute'])
    .config(
        function ($routeProvider, $httpProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'employeeTree.html',
                    controller: 'employeeTreeController',
                    controllerAs: 'controller'
                })
                .when('/employeeTree', {
                    templateUrl: 'employeeTree.html',
                    controller: 'employeeTreeController',
                    controllerAs: 'controller'
                })
                .when('/addEmployee', {
                    templateUrl: 'addEmployee.html',
                    controller: 'addEmployeeController',
                    controllerAs: 'controller'
                })
                .when('/addSubordinate/:leaderId', {
                    templateUrl: 'addSubordinate.html',
                    controller: 'addSubordinateController',
                    controllerAs: 'controller'
                })
                .when('/editEmployee/:employeeId', {
                    templateUrl: 'editEmployee.html',
                    controller: 'editEmployeeController',
                    controllerAs: 'controller'
                })
                .otherwise('/');

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        });
