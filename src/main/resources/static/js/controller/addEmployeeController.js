angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http) {
            var self = this;
            self.employee={};

            self.addEmployee=function () {
                console.log('EMPLOYEE: '+JSON.stringify(self.employee));
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
