angular.module('employeeManagementApp').service('employeeTreeService', ['$http', function (http) {
    var url = globalUrl + 'admin/tree';
    this.getTree = function () {
        return http.get(url);
    };
}]);