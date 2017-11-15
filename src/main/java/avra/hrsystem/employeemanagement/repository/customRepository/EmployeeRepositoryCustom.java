package avra.hrsystem.employeemanagement.repository.customRepository;

import avra.hrsystem.employeemanagement.model.Employee;

import java.util.Set;

public interface EmployeeRepositoryCustom {
    Set<Employee> getTree();
}
