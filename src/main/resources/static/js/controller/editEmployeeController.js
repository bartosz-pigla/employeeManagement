angular.module('employeeManagementApp')
    .controller('editEmployeeController',
        function ($scope, $http, $routeParams) {
            var id=$routeParams.employeeId;
            var self=this;

            self.employee={employeeId: 2, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [], leaderId: 1};

            console.log('EMPLOYEE ID: '+id);

            $scope.editEmployee=function (data) {
                console.log('EDITING EMPLOYEE: '+JSON.stringify(data));
            };

            self.tree = [
                {
                    employeeId: 1, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [
                    {employeeId: 2, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: []},
                    {employeeId: 3, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: []},
                    {employeeId: 4, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [
                        {employeeId: 5, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: [
                            {employeeId: 6, lastName: 'Darecka', firstName: 'Anna', dateOfEmployment: new Date(), subordinate: []}
                        ]}
                    ]}
                ]
                }
            ];
        });