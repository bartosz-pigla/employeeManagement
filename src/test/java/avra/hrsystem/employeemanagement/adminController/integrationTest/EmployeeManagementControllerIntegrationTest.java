package avra.hrsystem.employeemanagement.adminController.integrationTest;

import avra.hrsystem.employeemanagement.EmployeeManagementApplication;
import avra.hrsystem.employeemanagement.controller.EmployeeManagementController;
import avra.hrsystem.employeemanagement.controller.EmployeeTreeController;
import avra.hrsystem.employeemanagement.controller.GlobalControllerExceptionHandler;
import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.model.builder.EmployeeBuilder;
import avra.hrsystem.employeemanagement.model.dto.ErrorMessage;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeManagementControllerIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MessageSource messageSource;

    private MockMvc mockMvc;

    @Before
    public void before() {
        EmployeeManagementController employeeTreeController = new EmployeeManagementController(employeeRepository);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(employeeTreeController)
                .setControllerAdvice(new GlobalControllerExceptionHandler(messageSource))
                .build();
    }

    @Test
    public void createEmployee_ShouldReturnEmptyValidationErrors_WhenPostEmptyEmployee() throws Exception {
        mockMvc.perform(
                post("/admin/employee").contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("lastName", "firstName", "dateOfEmployment")))
                .andExpect(jsonPath("$[*].description", containsInAnyOrder("may not be null", "may not be null", "may not be null")));
    }

    @Test
    public void createEmployee_ShouldReturnTooLongValidationErrors_WhenPostEmptyEmployee() throws Exception {
        mockMvc.perform(
                post("/admin/employee").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"AnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaa\"," +
                                "\"lastName\":\"AnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaa\"," +
                                "\"dateOfEmployment\":\"2011-01-01\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is( "firstName")))
                .andExpect(jsonPath("$[0].description", is( "length must be between 0 and 30")))
                .andExpect(jsonPath("$[1].name", is( "lastName")))
                .andExpect(jsonPath("$[1].description", is("length must be between 0 and 50")));
    }

    @Test
    public void createEmployee_ShouldSaveEmployee() throws Exception {
        mockMvc.perform(
                post("/admin/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new EmployeeBuilder()
                                        .setFirstName("a")
                                        .setLastName("b")
                                        .setDateOfEmployment(new Date())
                                        .createEmployee()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployee_ShouldUpdateEmployee() throws Exception {
        mockMvc.perform(
                put("/admin/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new EmployeeBuilder()
                                        .setEmployeeId(1)
                                        .setFirstName("a")
                                        .setLastName("b")
                                        .setDateOfEmployment(new Date())
                                        .createEmployee()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployee_ShouldNotUpdateEmployee_WhenEmployeeIdNotExists() throws Exception {
        mockMvc.perform(
                put("/admin/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new EmployeeBuilder()
                                        .setEmployeeId(-1)
                                        .setFirstName("a")
                                        .setLastName("b")
                                        .setDateOfEmployment(new Date())
                                        .createEmployee()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is( "employeeId")))
                .andExpect(jsonPath("$[0].description", is( "employee id does not exists")));
    }

    @Test
    public void deleteTree_ShouldDelete_WhenEmployeeIdExists() throws Exception{
        mockMvc.perform(delete("/admin/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    public void deleteTree_ShouldNotDelete_WhenEmployeeIdNotExists() throws Exception{
        mockMvc.perform(delete("/admin/employee/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is( "employeeId")))
                .andExpect(jsonPath("$[0].description", is( "employee id does not exists")));
    }

}
