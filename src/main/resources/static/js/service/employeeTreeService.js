angular.module('employeeManagementApp').service('employeeTreeService', ['$http', function (http) {
    var url = globalUrl + 'admin/tree';
    this.getTree = function () {
        console.log(url);
        return http.get(url);
    };
}]);