package avra.hrsystem.employeemanagement.adminController.integrationTest;

import avra.hrsystem.employeemanagement.EmployeeManagementApplication;
import avra.hrsystem.employeemanagement.controller.EmployeeManagementController;
import avra.hrsystem.employeemanagement.controller.EmployeeTreeController;
import avra.hrsystem.employeemanagement.controller.GlobalControllerExceptionHandler;
import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.model.builder.EmployeeBuilder;
import avra.hrsystem.employeemanagement.model.builder.PairBuilder;
import avra.hrsystem.employeemanagement.model.dto.ErrorMessage;
import avra.hrsystem.employeemanagement.model.dto.Pair;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import avra.hrsystem.employeemanagement.service.StringToJsonConverter;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
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

    @Autowired
    private StringToJsonConverter converter;

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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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

    @Ignore
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createEmployee_ShouldReturnTooLongValidationErrors_WhenPostEmptyEmployee() throws Exception {
        List<String> expectedResults = new ArrayList<>(2);
        expectedResults.add(
                converter.convert(
                        new PairBuilder()
                                .setKey("name").setValue("firstName")
                                .setKey("description").setValue("length must be between 0 and 30")
                                .createPairs(),StringToJsonConverter::convertFieldWithoutDoubleQuote
                )

        );

        expectedResults.add(
                converter.convert(
                        new PairBuilder()
                                .setKey("name").setValue("lastName")
                                .setKey("description").setValue("length must be between 0 and 50")
                                .createPairs(),StringToJsonConverter::convertFieldWithoutDoubleQuote
                )

        );

        String input=
                converter.convert(new PairBuilder()
                .setKey("firstName").setValue("AnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaa")
                .setKey("lastName").setValue("AnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaaAnnAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaAnnaa")
                .setKey("dateOfEmployment").setValue("2011-01-01")
                .createPairs()
        );

        mockMvc.perform(
                post("/admin/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(expectedResults.get(0),expectedResults.get(1))));

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteTree_ShouldDelete_WhenEmployeeIdExists() throws Exception {
        mockMvc.perform(delete("/admin/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteTree_ShouldNotDelete_WhenEmployeeIdNotExists() throws Exception {
        mockMvc.perform(delete("/admin/employee/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"));
    }

}
