package avra.hrsystem.employeemanagement.repository;

import avra.hrsystem.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
}
