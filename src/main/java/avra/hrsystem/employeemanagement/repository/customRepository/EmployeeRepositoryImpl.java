package avra.hrsystem.employeemanagement.repository.customRepository;

import avra.hrsystem.employeemanagement.model.Employee;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Set;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Set<Employee> getTree() {
        Session session = entityManager.unwrap(Session.class);

        return Collections.emptySet();
    }
}
