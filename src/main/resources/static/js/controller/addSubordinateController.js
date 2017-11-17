angular.module('employeeManagementApp')
    .controller('addSubordinateController',
        function ($scope, $http, $routeParams) {
            var id=$routeParams.leaderId;
            var self=this;
            self.employee={};

            console.log('LEADER ID: '+id);

            self.addSubordinate=function () {
                console.log('ADD SUBORDINATE: '+JSON.stringify(self.employee));
            };
        });