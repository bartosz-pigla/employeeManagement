package avra.hrsystem.employeemanagement.repository.customRepository;

import avra.hrsystem.employeemanagement.exceptions.WrongFieldException;
import avra.hrsystem.employeemanagement.model.Employee;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{
    private EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> getTree() {
        Session session = entityManager.unwrap(Session.class);

        Query query=session.createQuery("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.subordinate");

        List<Employee> employees=query.list();

        return employees.stream().filter(e->e.getLeader()==null).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getTree(String orderField){
        Session session = entityManager.unwrap(Session.class);

        List<Employee> employees=null;
        try{
            employees=session.createCriteria(Employee.class)
                    .setFetchMode("subordinate", FetchMode.JOIN)
                    .addOrder(Order.asc(orderField))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
        }
        catch (Exception ex){
            Optional<Field> field = Arrays.stream(Employee.class.getDeclaredFields()).filter(f->f.getName().equals(orderField)).findFirst();
            if(field.isPresent()){
                throw ex;
            }
            else{
                throw new WrongFieldException(orderField);
            }
        }

        return employees.stream().filter(e->e.getLeader()==null).collect(Collectors.toList());
    }

    @Override
    public boolean update(Employee employee) {
        Session session = entityManager.unwrap(Session.class);

        Employee old=session.get(Employee.class,employee.getEmployeeId());
        if(old!=null){
            session.evict(old);
            session.update(employee);
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public boolean remove(Integer id) {
        Session session = entityManager.unwrap(Session.class);

        Query query=session.createQuery("FROM Employee e LEFT JOIN FETCH e.subordinate WHERE e.employeeId=:employeeId")
                .setInteger("employeeId",id);

        List<Employee> employees=query.setMaxResults(1).list();
        if(employees!=null && employees.size()==1){
            Employee employeeToRemove=employees.get(0);
            employeeToRemove.setLeader(null);

            List<Employee> subordinates=employeeToRemove.getSubordinate();
            if(subordinates!=null && subordinates.size()>0){
                subordinates.forEach(s->s.setLeader(null));
            }

            session.delete(employeeToRemove);

            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean assignSubordinateToLeader(Integer subordinateId, Integer leaderId) {
        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("FROM Employee e where e.employeeId in (:ids)").setParameterList("ids", new Integer[]{subordinateId,leaderId});
        List<Employee> list=query.list();

        Optional<Employee> subordinate = list.stream().filter(e->e.getEmployeeId().equals(subordinateId)).findFirst();
        if(subordinate.isPresent()){
            Optional<Employee> leader = list.stream().filter(e->e.getEmployeeId().equals(leaderId)).findFirst();
            if(leader.isPresent()){
                Employee s=subordinate.get();
                s.setLeader(leader.get());
                session.update(s);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unAssignSubordinateToLeader(Integer subordinateId) {
        Session session = entityManager.unwrap(Session.class);

        Employee subordinate=session.get(Employee.class,subordinateId);
        if(subordinate!=null){
            subordinate.setLeader(null);
            session.update(subordinate);
            return true;
        }
        return false;
    }

    @Override
    public boolean unAssignAllSubordinates(Integer leaderId) {
        Session session = entityManager.unwrap(Session.class);

        Query query=session.createQuery("FROM Employee e LEFT JOIN FETCH e.subordinate WHERE e.employeeId=:employeeId")
                .setInteger("employeeId",leaderId);

        List<Employee> employees=query.setMaxResults(1).list();
        if(employees!=null && employees.size()==1){
            Employee leader=employees.get(0);

            List<Employee> subordinates=leader.getSubordinate();
            if(subordinates!=null && subordinates.size()>0){
                subordinates.forEach(s->{
                    s.setLeader(null);
                    session.update(s);
                });
            }

            return true;
        }
        else{
            return false;
        }
    }
}
