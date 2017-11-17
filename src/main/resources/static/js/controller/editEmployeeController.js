angular.module('employeeManagementApp')
    .controller('editEmployeeController',
        function ($scope, $http, $routeParams,$location, employeeService, employeeTreeService) {
            var id=$routeParams.employeeId;
            var self=this;
            $scope.edited=null;
            $scope.assigned=null;
            $scope.initialLeaderId=null;
            self.employee={};

            employeeService.getEmployee(id).then(function (response) {
                self.employee=response.data;
                self.employee.dateOfEmployment=new Date(self.employee.dateOfEmployment);
                employeeService.getLeaderId(self.employee.employeeId).then(function (response) {
                    if(response.status===200){
                        self.employee.leader={};
                        self.employee.leader.leaderId=response.data;
                        $scope.initialLeaderId=response.data;
                    }
                })
            });

            //self.employee={employeeId: 2, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [], leaderId: 1};

            console.log('EMPLOYEE ID: '+id);

            self.editEmployee=function () {
                console.log('EDITING EMPLOYEE: '+JSON.stringify(self.employee));
                employeeService.updateEmployee(self.employee).then(function (updateResponse) {
                    if(updateResponse.status===200){
                        console.log('EDIT: '+JSON.stringify(updateResponse));
                        $scope.edited=true;
                        //self.employee=updateResponse.data;
                        if(self.employee.leader!==null){
                            console.log('BEFORE ASSIGN: ');
                            employeeService.assignSubordinateToLeader(self.employee.employeeId,self.employee.leader.leaderId).then(function (assignmentResponse) {
                                console.log('ASSIGN: '+JSON.stringify(assignmentResponse));
                                $scope.assigned=assignmentResponse.status===200;
                                if($scope.assigned){
                                    $location.path('/employeeTree');
                                }
                            });
                        }
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

            // self.tree = [
            //     {
            //         employeeId: 1, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [
            //         {employeeId: 2, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: []},
            //         {employeeId: 3, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: []},
            //         {employeeId: 4, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [
            //             {employeeId: 5, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [
            //                 {employeeId: 6, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: []}
            //             ]}
            //         ]}
            //     ]
            //     }
            // ];
        });