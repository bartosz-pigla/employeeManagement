angular.module('employeeManagementApp')
    .controller('employeeTreeController',
        function ($scope, $http, $location) {
            var self = this;
            var employeeToDelete=null;
            var subordinates=null;

            $scope.addSubordinate=function (data) {
                console.log('SUBORDINATE EMPLOYEE: '+data.employeeId);
                $location.path('/addSubordinate/' + data.employeeId);
            };

            $scope.deleteEmployee=function () {
                console.log('ASSIGNED EMPLOYEE: ');
            };

            $scope.unassignAllSubordinates=function () {
                console.log('unassign');
            };

            $scope.markEmployeeToDelete=function (data) {
                console.log('employee to delete marked');
                employeeToDelete=data;
            };

            $scope.markSubordinatesToUnassign=function (data) {
                console.log('subordinates to delete marked');
                subordinates=data;
            };

            $scope.editEmployee=function (data) {
                $location.path('/editEmployee/' + data.employeeId);
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