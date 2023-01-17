package ua.foxminded.javaspring.schoolconsoleapp;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootTest(classes = TestConfig.class)
@ContextConfiguration(initializers = MyContainer.Initializer.class)
public class IntegrationTestBase {

    @Autowired
    JdbcTemplate jdbc;

    static DataSource dataSource;

    @BeforeAll
    static void init() {
        MyContainer.container.start();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(MyContainer.container.getJdbcUrl());
        config.setUsername(MyContainer.container.getUsername());
        config.setPassword(MyContainer.container.getPassword());
        config.setDriverClassName(MyContainer.container.getDriverClassName());
        dataSource = new HikariDataSource(config);
    }
}
