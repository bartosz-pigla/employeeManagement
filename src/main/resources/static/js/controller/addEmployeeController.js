angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http, employeeService) {
            var self = this;
            self.employee={};

            self.addEmployee=function () {
                console.log('EMPLOYEE: '+JSON.stringify(self.employee));

                employeeService.createEmployee(self.employee).then(function (response) {
                    console.log('response: '+JSON.stringify(response));
                    
                });
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

        })
;
