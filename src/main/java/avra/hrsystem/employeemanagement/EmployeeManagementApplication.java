package avra.hrsystem.employeemanagement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableTransactionManagement
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Profile("dev")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/****").allowedOrigins("http://localhost:63342");
            }
        };
    }

    @Bean(name = "APP_URL")
    @Profile("prod")
    public String appUrl() {
        return System.getenv("APP_URL");
    }

    @Bean(name = "restTemplateForHeroku")
    @Profile("prod")
    public RestTemplate restTemplateForHerokuServerSleepingPrevention() {
        return new RestTemplate();
    }

    @Bean
    @Profile("prod")
    public Timer preventHerokuFromSleepBean(
            @Qualifier(value = "APP_URL") String appUrl,
            @Qualifier(value = "restTemplateForHeroku") RestTemplate restTemplate
    ) {
        String restUrl = appUrl.substring(0, appUrl.length() - 2);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("LOG FOR THE SAKE OF HEROKU");
                restTemplate.getForObject(restUrl + "fake", String.class);
            }
        }, 0, 1000 * 3);
        return timer;
    }
}
