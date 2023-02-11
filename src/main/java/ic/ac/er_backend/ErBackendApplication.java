package ic.ac.er_backend;

import io.github.MigadaTang.ER;
import io.github.MigadaTang.common.RDBMSType;
import io.github.MigadaTang.exception.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class ErBackendApplication {

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws IOException, SQLException, ParseException {
        ER.initialize(RDBMSType.POSTGRESQL, "db.doc.ic.ac.uk", "5432", "wh722", "wh722", "4jC@A3528>0N6");
    }

    public static void main(String[] args) {
        SpringApplication.run(ErBackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
