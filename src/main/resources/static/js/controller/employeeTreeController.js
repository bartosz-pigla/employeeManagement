angular.module('employeeManagementApp')
    .controller('employeeTreeController',
        function ($scope, $http, $location, employeeTreeService, employeeService) {
            var self = this;
            var employeeToDelete=null;
            var leader=null;
            self.treeLoadingSuccess=null;
            $scope.deleted=null;
            $scope.unassigned=null;

            employeeTreeService.getTree().then(function (response) {
                console.log('GET TREE: '+JSON.stringify(response));
                self.treeLoadingSuccess=response.status===200;
                if(self.treeLoadingSuccess){
                    console.log('TREE FROM SERVICE: '+response.data);
                    $scope.tree=response.data;
                }
            });

            $scope.addSubordinate=function (data) {
                console.log('SUBORDINATE EMPLOYEE: '+data.employeeId);
                $location.path('/addSubordinate/' + data.employeeId);
            };

            $scope.deleteEmployee=function () {
                console.log('DELETE EMPLOYEE: '+JSON.stringify(employeeToDelete));
                employeeService.deleteEmployee(employeeToDelete.employeeId).then(function (response) {
                    $scope.deleted=response.status===200;
                    console.log('DELETED: '+self.deleted);
                    $scope.tree.forEach(function (e) { if(e.employeeId===employeeToDelete.employeeId){e.subordinate.forEach(function (s) { s.leaderId=null;$scope.tree.push(s); })} });
                    $scope.tree=$scope.tree.filter(function (e) { return e.employeeId!==employeeToDelete.employeeId; });
                });
            };

            $scope.unassignAllSubordinates=function () {
                console.log('UNASSIGN EMPLOYEE: '+JSON.stringify(leader));
                employeeService.unassignAllSubordinates(leader.employeeId).then(function (response) {
                    $scope.unassigned=response.status===200;
                    console.log('UNASSIGNED: '+self.unassigned);
                    leader.subordinate.forEach(function (s) { s.leaderId=null; $scope.tree.push(s);});
                    leader.subordinate=null;
                });
            };

            $scope.markEmployeeToDelete=function (data) {
                console.log('employee to delete marked');
                employeeToDelete=data;
            };

            $scope.markSubordinatesToUnassign=function (data) {
                console.log('leader to delete marked: '+JSON.stringify(data));
                leader=data;
            };

            $scope.editEmployee=function (data) {
                $location.path('/editEmployee/' + data.employeeId);
            };
        });