package avra.hrsystem.employeemanagement.adminController.unitTest;

import avra.hrsystem.employeemanagement.controller.EmployeeManagementController;
import avra.hrsystem.employeemanagement.controller.EmployeeTreeController;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
}
