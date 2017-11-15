package avra.hrsystem.employeemanagement.adminController.integrationTest;

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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class AdminControllerIntegerationTest {
	@Autowired
	private EmployeeRepository employeeRepository;

	private MockMvc mockMvc;

	@Before
	public void before(){
		AdminController adminController=new AdminController(employeeRepository);
		this.mockMvc= MockMvcBuilders.standaloneSetup(adminController).build();
	}


	@Test
	public void getTree_ShouldReturnEmptyResponse_WhenUnimplemented() throws Exception{
		mockMvc.perform(get("/admin/tree"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$",hasSize(0)));
	}

}
