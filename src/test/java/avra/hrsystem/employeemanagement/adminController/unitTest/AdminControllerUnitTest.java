package avra.hrsystem.employeemanagement.adminController.unitTest;

import avra.hrsystem.employeemanagement.controller.AdminController;
import avra.hrsystem.employeemanagement.exceptions.WrongFieldException;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.*;
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
	private EmployeeRepository employeeRepository;

	private AdminController adminController;

	@Before
	public void before(){
		adminController=new AdminController(employeeRepository);
	}

	@Ignore
	@Test
	public void getTree_ShouldReturnEmptySet_WhenUnimplemented() throws Exception{
		assertEquals(adminController.getTree().size(),0);
	}

	@Test
	public void getTree_ShouldReturn2Roots() throws Exception{
		assertEquals(adminController.getTree().size(),2);
	}

	@Test(expected = WrongFieldException.class)
	public void getTree_ShouldThrowWrongFieldException() throws Exception{
		adminController.getOrderedTree("wrongField");
	}

	@Test
	public void getTree_ShouldReturnEmptyTree_WhenNoEmployees() throws Exception{
		employeeRepository.deleteAll();
		assertEquals(adminController.getTree().size(),0);
	}

	@Test
	public void getTree_ShouldReturnEmptyOrderedTree_WhenNoEmployees() throws Exception{
		employeeRepository.deleteAll();
		assertEquals(adminController.getOrderedTree("employeeId").size(),0);
	}

}
