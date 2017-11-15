package avra.hrsystem.employeemanagement.adminController.unitTest;

import avra.hrsystem.employeemanagement.controller.AdminController;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class AdminControllerUnitTest {
	@Autowired
	private static EmployeeRepository employeeRepository;

	private static AdminController adminController;

	@BeforeClass
	public static void beforeClass(){
		AdminController adminController=new AdminController(employeeRepository);
	}

	@Test
	public void getTree_ShouldReturnEmptySet_WhenUnimplemented() throws Exception{
		assertEquals(adminController.getTree().size(),0);
	}

}
