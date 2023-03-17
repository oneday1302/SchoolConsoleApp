package ua.foxminded.javaspring.schoolconsoleapp.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ua.foxminded.javaspring.schoolconsoleapp.dao")
@EnableAutoConfiguration
public class DaoTestConfig {
   
}
