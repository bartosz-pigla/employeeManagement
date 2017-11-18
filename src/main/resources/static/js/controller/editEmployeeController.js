angular.module('employeeManagementApp')
    .controller('editEmployeeController',
        function ($scope, $http, $routeParams,$location, employeeService, employeeTreeService) {
            var id=$routeParams.employeeId;
            $scope.edited=null;
            $scope.assigned=null;
            $scope.initialLeaderId=null;
            $scope.employee={};

            employeeService.getEmployee(id).then(function (response) {
                $scope.employee=response.data;
                $scope.employee.dateOfEmployment=new Date($scope.employee.dateOfEmployment);
                employeeService.getLeaderId($scope.employee.employeeId).then(function (response) {
                    if(response.status===200){
                        $scope.employee.leader={};
                        $scope.employee.leader.employeeId=response.data;
                        $scope.initialLeaderId=response.data;
                    }
                })
            });

            console.log('EMPLOYEE ID: '+id);

            $scope.editEmployee=function () {
                employeeService.updateEmployee($scope.employee).then(function (updateResponse) {
                    if(updateResponse.status===200){
                        console.log('EDIT: '+JSON.stringify(updateResponse));
                        $scope.edited=true;
                        $location.path('/employeeTree');
                    }
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
        });