angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http,employeeTreeService, employeeService, $location) {
            $scope.employee={};
            $scope.employee.leader={};
            $scope.added=null;

            $scope.addEmployee=function () {
                employeeService.createEmployee($scope.employee).then(function (response) {
                    $scope.added=response.status===200;
                    $location.path('/employeeTree');
                });
            };

            employeeTreeService.getTree().then(function (response) {
                $scope.treeLoadingSuccess=response.status===200;
                if($scope.treeLoadingSuccess){
                    $scope.tree=response.data;
                }
            });
        })
;
