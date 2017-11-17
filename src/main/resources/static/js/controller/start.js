var pizzaShopManagementApp = angular.module('employeeManagementApp',['ngRoute'])
    .config(
        function ($routeProvider, $httpProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'employeeList.html',
                    controller: 'employeeListController',
                    controllerAs: 'controller'
                })
                .when('/employeeList', {
                    templateUrl: 'employeeList.html',
                    controller: 'employeeListController',
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
                .when('/editEmployee', {
                    templateUrl: 'editEmployee.html',
                    controller: 'editEmployeeController',
                    controllerAs: 'controller'
                })
                .otherwise('/');

            $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        });
