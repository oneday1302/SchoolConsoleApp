package ua.foxminded.javaspring.schoolconsoleapp.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ua.foxminded.javaspring.schoolconsoleapp.dao")
@EntityScan("ua.foxminded.javaspring.schoolconsoleapp.entity")
@EnableJpaRepositories("ua.foxminded.javaspring.schoolconsoleapp.dao")
@EnableAutoConfiguration
public class DaoTestConfig {
    
}
