package avra.hrsystem.employeemanagement.repository;

import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.repository.customRepository.EmployeeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Integer>, EmployeeRepositoryCustom{
    @Query("SELECT e FROM Employee e INNER JOIN FETCH e.subordinate WHERE e.employeeId = :id")
    Employee findOneAndFetchSubordinates(@Param("id")Integer id);

    @Query("SELECT e.leader.employeeId FROM Employee e WHERE e.employeeId = :subordinateId")
    Integer findLeaderIdOfSubordinate(@Param("subordinateId") Integer subordinateId);
}
