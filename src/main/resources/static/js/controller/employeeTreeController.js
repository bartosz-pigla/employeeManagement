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
                    console.log('GET TREE: '+JSON.stringify(response));
                    $scope.treeLoadingSuccess=response.status===200;
                    if($scope.treeLoadingSuccess){
                        console.log('TREE FROM SERVICE: '+response.data);
                        $scope.tree=response.data;
                    }
                });
            };

            getTree();

            $scope.addSubordinate=function (data) {
                console.log('SUBORDINATE EMPLOYEE: '+data.employeeId);
                $location.path('/addSubordinate/' + data.employeeId);
            };

            $scope.deleteEmployee=function () {
                console.log('DELETE EMPLOYEE: '+JSON.stringify(employeeToDelete));
                employeeService.deleteEmployee(employeeToDelete.employeeId).then(function (response) {
                    $scope.deleted=response.status===200;
                    console.log('DELETED: '+$scope.deleted);

                    getTree();

                });
            };

            $scope.unassignAllSubordinates=function () {
                console.log('UNASSIGN EMPLOYEE: '+JSON.stringify(leader));
                employeeService.unassignAllSubordinates(leader.employeeId).then(function (response) {
                    $scope.unassigned=response.status===200;
                    console.log('UNASSIGNED: '+$scope.unassigned);
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