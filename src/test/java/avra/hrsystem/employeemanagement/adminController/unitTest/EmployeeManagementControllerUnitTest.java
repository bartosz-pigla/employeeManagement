package avra.hrsystem.employeemanagement.adminController.unitTest;

import avra.hrsystem.employeemanagement.controller.EmployeeManagementController;
import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class EmployeeManagementControllerUnitTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    private EmployeeManagementController employeeManagementController;

    @Before
    public void before(){
        employeeManagementController =new EmployeeManagementController(employeeRepository);
    }

    @Test
    public void getEmployees_ShouldReturnOnePageOf5Employees() throws Exception{
        assertEquals(employeeManagementController.read(5,1).size(),5);
    }

    @Test
    public void getEmployees_ShouldReturn4Employees_WhenFromLastPageOfSize5() throws Exception{
        assertEquals(employeeManagementController.read(5,2).size(),4);
    }

    @Test
    public void getEmployees_ShouldReturnNoEmployees_WhenPageNumberIsTooLong() throws Exception{
        assertEquals(employeeManagementController.read(5,4).size(),0);
    }

    @Test
    public void getEmployees_ShouldReturnEmployees_WhenPageNumberIsNull() throws Exception{
        assertEquals(employeeManagementController.read(4,null).size(),4);
    }

    @Test
    public void getEmployees_ShouldReturnAllEmployees_WhenPageSizeIsAndPageNumberNull() throws Exception{
        assertEquals(employeeManagementController.read(null,null).size(),9);
    }

    @Test
    public void getEmployees_ShouldReturnAllEmployees_WhenPageSizeIsNull() throws Exception{
        assertEquals(employeeManagementController.read(null,3).size(),9);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void assignSubordinate_ShouldAssign_WhenSubordinateAndLeaderExists() throws Exception{
        employeeManagementController.assign(1,2);
        Employee employee=employeeRepository.findOne(1);
        assertEquals(employee.getLeader().getEmployeeId(),new Integer(2));
    }
    @Test
    public void assignSubordinate_ShouldNotAssign_WhenSubordinateAndLeaderHasTheSameId() throws Exception{
        employeeManagementController.assign(1,3);
        //Employee employee=employeeRepository.findOne(1);
        Employee employee=employeeRepository.findOneAndFetchSubordinates(3);
        assertNotEquals(employee.getEmployeeId(),new Integer(1));
    }

    @Test
    public void assignSubordinate_ShouldNotAssign_WhenSubordinateAndLeaderDoesNotExists() throws Exception{
        ResponseEntity<Void> response= employeeManagementController.assign(-1,-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void assignSubordinate_ShouldNotAssign_WhenSubordinateDoesNotExists() throws Exception{
        ResponseEntity<Void> response= employeeManagementController.assign(-1,1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void assignSubordinate_ShouldNotAssign_WhenLeaderDoesNotExists() throws Exception{
        ResponseEntity<Void> response= employeeManagementController.assign(1,-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void unAssignSubordinate_ShouldUnAssign_WhenSubordinateExists() throws Exception{
        int employeeId=1;
        ResponseEntity<Void> response = employeeManagementController.unAssign(employeeId);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
        Employee employee=employeeRepository.findOneAndFetchSubordinates(employeeId);
        assertNull(employee.getLeader());
    }

    @Test
    public void unAssignSubordinate_ShouldNotUnAssign_WhenSubordinateNotExists() throws Exception{
        int employeeId=-1;
        ResponseEntity<Void> response= employeeManagementController.unAssign(employeeId);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void unAssignAllSubordinates_ShouldUnAssign_WhenLeaderExists() throws Exception{
        int leaderId=1;
        ResponseEntity<Void> response = employeeManagementController.unAssignAllSubordinates(leaderId);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void unAssignAllSubordinates_ShouldUnAssign_WhenLeaderNotExists() throws Exception{
        int leaderId=-1;
        ResponseEntity<Void> response = employeeManagementController.unAssignAllSubordinates(leaderId);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
