package avra.hrsystem.employeemanagement.repository.customRepository;

import avra.hrsystem.employeemanagement.exceptions.WrongFieldException;
import avra.hrsystem.employeemanagement.model.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeRepositoryCustom {
    List<Employee> getTree();

    List<Employee> getTree(String orderField);

    boolean update(Employee employee);

    boolean remove(Integer id);

    boolean assignSubordinateToLeader(Integer subordinateId, Integer leaderId);

    boolean unAssignSubordinateToLeader(Integer subordinateId);

    boolean unAssignAllSubordinates(Integer leaderId);
}
