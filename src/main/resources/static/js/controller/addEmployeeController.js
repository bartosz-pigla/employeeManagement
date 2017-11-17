angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http,employeeTreeService, employeeService) {
            var self = this;
            self.employee={};
            $scope.added=null;

            self.addEmployee=function () {
                console.log('EMPLOYEE: '+JSON.stringify(self.employee));

                employeeService.createEmployee(self.employee).then(function (response) {
                    console.log('response: '+JSON.stringify(response));
                    $scope.added=response.status===200;
                    if(response.status===200){
                        self.employee={};
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
