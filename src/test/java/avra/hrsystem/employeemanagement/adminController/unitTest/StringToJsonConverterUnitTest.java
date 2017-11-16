package avra.hrsystem.employeemanagement.adminController.unitTest;

import avra.hrsystem.employeemanagement.model.builder.PairBuilder;
import avra.hrsystem.employeemanagement.service.StringToJsonConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class StringToJsonConverterUnitTest {
    @Autowired
    private StringToJsonConverter converter;

    @Test
    public void convert_shouldReturnValidJson() throws Exception{
        String expected="{\"firstName\":\"Anna\", \"lastName\":\"B\", \"dateOfEmployment\":\"2011-01-01\"}";
        String actual=converter.convert(new PairBuilder()
                .setKey("firstName").setValue("Anna")
                .setKey("lastName").setValue("B")
                .setKey("dateOfEmployment").setValue("2011-01-01")
                .createPairs()
        );
        assertEquals(expected,actual);
    }
}
