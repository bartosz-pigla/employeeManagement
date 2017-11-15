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
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Employee> getTree() {
        Session session = entityManager.unwrap(Session.class);

        Query query=session.createQuery("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.subordinate");

        List<Employee> employess=query.list();

        return employess.stream().filter(e->e.getLeader()==null).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getTree(String orderField){
        Session session = entityManager.unwrap(Session.class);

        List<Employee> employess=null;
        try{
            employess=session.createCriteria(Employee.class)
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

        return employess.stream().filter(e->e.getLeader()==null).collect(Collectors.toList());
    }
}
