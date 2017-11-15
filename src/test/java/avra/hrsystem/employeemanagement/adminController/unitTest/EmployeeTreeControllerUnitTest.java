package avra.hrsystem.employeemanagement.adminController.unitTest;

import avra.hrsystem.employeemanagement.controller.EmployeeTreeController;
import avra.hrsystem.employeemanagement.exceptions.WrongFieldException;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class EmployeeTreeControllerUnitTest {
	@Autowired
	private EmployeeRepository employeeRepository;

	private EmployeeTreeController employeeTreeController;

	@Before
	public void before(){
		employeeTreeController =new EmployeeTreeController(employeeRepository);
	}

	@Ignore
	@Test
	public void getTree_ShouldReturnEmptySet_WhenUnimplemented() throws Exception{
		assertEquals(employeeTreeController.getTree().size(),0);
	}

	@Test
	public void getTree_ShouldReturn2Roots() throws Exception{
		assertEquals(employeeTreeController.getTree().size(),2);
	}

	@Test(expected = WrongFieldException.class)
	public void getTree_ShouldThrowWrongFieldException() throws Exception{
		employeeTreeController.getOrderedTree("wrongField");
	}

	@Test
	public void getTree_ShouldReturnEmptyTree_WhenNoEmployees() throws Exception{
		employeeRepository.deleteAll();
		assertEquals(employeeTreeController.getTree().size(),0);
	}

	@Test
	public void getTree_ShouldReturnEmptyOrderedTree_WhenNoEmployees() throws Exception{
		employeeRepository.deleteAll();
		assertEquals(employeeTreeController.getOrderedTree("employeeId").size(),0);
	}

}
