angular.module('employeeManagementApp')
    .controller('employeeTreeController',
        function ($scope, $http) {
            var self = this;
            var employeeToDelete=null;

            $scope.addEmployee=function () {
                console.log('ASSIGNED EMPLOYEE: ');
            };

            $scope.deleteEmployee=function () {
                console.log('ASSIGNED EMPLOYEE: ');
            };

            $scope.unassignAllSubordinates=function () {
                console.log('unassign');
            };

            $scope.markEmployeeToDelete=function (data) {
                employeeToDelete=data;
            };

            $scope.markSubordinatesToDelete=function (data) {
                markSubordinatesToDelete()
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
        });