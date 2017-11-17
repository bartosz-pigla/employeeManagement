angular.module('employeeManagementApp').service('employeeService', ['$http', function (http) {
    var url = globalUrl + 'admin/employee';
    this.getEmployee = function (employeeId) {
        return http.get(url + '/' + employeeId);
    };
    this.updateEmployee=function (employee) {
        return http.put(url,employee);
    };
    this.deleteEmployee=function (employeeId) {
        return http.delete(url+'/'+employeeId);
    };
    this.createEmployee=function (employee) {
        return http.post(url,employee);
    };
    this.unassignAllSubordinates=function (leaderId) {
        return http.put(url+'/unassignAllSubordinates/'+leaderId);
    };
    this.assignSubordinateToLeader=function (subordinateId, leaderId) {
        return http.put(url+'/assign/'+subordinateId+'/'+leaderId);
    };
    this.getLeaderId=function (subordinateId) {
        return http.get(url+'/leader/'+subordinateId);
    }
}]);