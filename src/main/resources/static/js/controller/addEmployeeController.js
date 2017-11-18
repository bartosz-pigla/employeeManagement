angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http,employeeTreeService, employeeService, $location) {
            $scope.employee={};
            $scope.employee.leader={};
            $scope.added=null;

            $scope.addEmployee=function () {
                console.log('EMPLOYEE: '+JSON.stringify($scope.employee));

                employeeService.createEmployee($scope.employee).then(function (response) {
                    console.log('response: '+JSON.stringify(response));
                    $scope.added=response.status===200;
                    $location.path('/employeeTree');
                });
            };

            employeeTreeService.getTree().then(function (response) {
                console.log('GET TREE: '+JSON.stringify(response));
                $scope.treeLoadingSuccess=response.status===200;
                if($scope.treeLoadingSuccess){
                    console.log('TREE FROM SERVICE: '+response.data);
                    $scope.tree=response.data;
                }
            });
        })
;
