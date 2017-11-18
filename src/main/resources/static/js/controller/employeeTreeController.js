angular.module('employeeManagementApp')
    .controller('employeeTreeController',
        function ($scope, $http, $location, employeeTreeService, employeeService) {
            var employeeToDelete=null;
            var leader=null;
            $scope.treeLoadingSuccess=null;
            $scope.deleted=null;
            $scope.unassigned=null;

            var getTree=function () {
                employeeTreeService.getTree().then(function (response) {
                    $scope.treeLoadingSuccess=response.status===200;
                    if($scope.treeLoadingSuccess){
                        $scope.tree=response.data;
                    }
                });
            };

            getTree();

            $scope.addSubordinate=function (data) {
                $location.path('/addSubordinate/' + data.employeeId);
            };

            $scope.deleteEmployee=function () {
                employeeService.deleteEmployee(employeeToDelete.employeeId).then(function (response) {
                    $scope.deleted=response.status===200;
                    getTree();
                });
            };

            $scope.unassignAllSubordinates=function () {
                employeeService.unassignAllSubordinates(leader.employeeId).then(function (response) {
                    $scope.unassigned=response.status===200;
                    leader.subordinate.forEach(function (s) { s.leaderId=null; $scope.tree.push(s);});
                    leader.subordinate=null;
                });
            };

            $scope.markEmployeeToDelete=function (data) {
                employeeToDelete=data;
            };

            $scope.markSubordinatesToUnassign=function (data) {
                leader=data;
            };

            $scope.editEmployee=function (data) {
                $location.path('/editEmployee/' + data.employeeId);
            };
        });