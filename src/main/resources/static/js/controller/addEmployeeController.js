angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http,employeeTreeService, employeeService, $location) {
            var self = this;
            $scope.employee={};
            $scope.added=null;

            self.addEmployee=function () {
                console.log('EMPLOYEE: '+JSON.stringify($scope.employee));

                employeeService.createEmployee($scope.employee).then(function (response) {
                    console.log('response: '+JSON.stringify(response));
                    $scope.added=response.status===200;
                    if(response.status===200){
                        $location.path('/employeeTree');
                    }
                });
            };

            employeeTreeService.getTree().then(function (response) {
                console.log('GET TREE: '+JSON.stringify(response));
                self.treeLoadingSuccess=response.status===200;
                if(self.treeLoadingSuccess){
                    console.log('TREE FROM SERVICE: '+response.data);
                    self.tree=response.data;
                }
            });
        })
;
