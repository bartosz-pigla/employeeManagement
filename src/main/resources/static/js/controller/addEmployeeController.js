angular.module('employeeManagementApp')
    .controller('addEmployeeController',
        function ($scope, $http) {
            var self = this;

            var assigned=null;

            $scope.assign=function (data) {
                console.log('ASSIGNED EMPLOYEE: '+JSON.stringify(data));
                assigned=data;
            };

            $scope.unassign=function () {
                console.log('unassign');

                assigned=null;
            };

            $scope.tree = [
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

            self.assignEmployee = function(data) {
                console.log(data);
            };

        })
;
