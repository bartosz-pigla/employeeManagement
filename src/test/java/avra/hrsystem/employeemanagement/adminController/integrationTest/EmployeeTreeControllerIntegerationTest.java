package avra.hrsystem.employeemanagement.adminController.integrationTest;

import avra.hrsystem.employeemanagement.controller.EmployeeTreeController;
import avra.hrsystem.employeemanagement.controller.GlobalControllerExceptionHandler;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTreeControllerIntegerationTest {
	@Autowired
	private EmployeeRepository employeeRepository;

	private MockMvc mockMvc;

	@Before
	public void before(){
		EmployeeTreeController employeeTreeController =new EmployeeTreeController(employeeRepository);
		this.mockMvc= MockMvcBuilders
				.standaloneSetup(employeeTreeController)
				.setControllerAdvice(new GlobalControllerExceptionHandler())
				.build();
	}


	@Ignore
	@Test
	public void getTree_ShouldReturnEmptyResponse_WhenUnimplemented() throws Exception{
		mockMvc.perform(get("/admin/tree"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$",hasSize(0)));
	}

	@Test
	public void getTreeOrderByEmployeeId_ShouldReturn2Roots() throws Exception{
		mockMvc.perform(get("/admin/tree/orderBy/employeeId"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$",hasSize(2)))
				.andExpect(jsonPath("$[*].employeeId",containsInAnyOrder(1,2)))

				.andExpect(jsonPath("$[0].subordinate",hasSize(2)))
				.andExpect(jsonPath("$[0].subordinate[*].employeeId",containsInAnyOrder(3,4)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate",hasSize(2)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate[0].employeeId",is(7)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate[0].subordinate",hasSize(0)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate[1].employeeId",is(8)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate[1].subordinate",hasSize(1)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate[1].subordinate[0].employeeId",is(9)))
				.andExpect(jsonPath("$[0].subordinate[0].subordinate[1].subordinate[0].subordinate",hasSize(0)))

				.andExpect(jsonPath("$[1].subordinate",hasSize(1)))
				.andExpect(jsonPath("$[1].subordinate[0].employeeId",is(5)))
				.andExpect(jsonPath("$[1].subordinate[0].subordinate",hasSize(1)))
				.andExpect(jsonPath("$[1].subordinate[0].subordinate[0].employeeId",is(6)))
				.andExpect(jsonPath("$[1].subordinate[0].subordinate[0].subordinate",hasSize(0)));
	}

	@Test
	public void getTree_ShouldReturn2Roots() throws Exception{
		mockMvc.perform(get("/admin/tree"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$",hasSize(2)))
				.andExpect(jsonPath("$[*].employeeId",containsInAnyOrder(1,2)));
	}

	@Test
	public void getTree_ShouldReturnErrorMessage() throws Exception{
		mockMvc.perform(get("/admin/tree/orderBy/wrongField"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.name",is("wrong field")))
				.andExpect(jsonPath("$.description",is("wrongField")));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void getTree_ShouldReturnEmptyTree_WhenNoEmployees() throws Exception{
		employeeRepository.deleteAll();

		mockMvc.perform(get("/admin/tree"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$",hasSize(0)));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void getTree_ShouldReturnEmptyOrderedTree_WhenNoEmployees() throws Exception{
		employeeRepository.deleteAll();
		mockMvc.perform(get("/admin/tree/orderBy/employeeId"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$",hasSize(0)));
	}

}
