package avra.hrsystem.employeemanagement.model.builder;

import avra.hrsystem.employeemanagement.model.Employee;

import java.util.Date;
import java.util.List;

public class EmployeeBuilder {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private Date dateOfEmployment;
    private Employee leader;
    private List<Employee> subordinate;

    public EmployeeBuilder setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public EmployeeBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
        return this;
    }

    public EmployeeBuilder setLeader(Employee leader) {
        this.leader = leader;
        return this;
    }

    public EmployeeBuilder setSubordinate(List<Employee> subordinate) {
        this.subordinate = subordinate;
        return this;
    }

    public Employee createEmployee() {
        return new Employee(employeeId, firstName, lastName, dateOfEmployment, leader, subordinate);
    }
}