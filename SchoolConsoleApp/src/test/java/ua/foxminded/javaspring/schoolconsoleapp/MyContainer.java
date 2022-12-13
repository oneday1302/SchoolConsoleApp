package ua.foxminded.javaspring.schoolconsoleapp;

import javax.sql.DataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

abstract class MyContainer {

    static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withInitScript("schemaTest.sql");
    static final DataSource dataSource;

    static {
        container.start();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(container.getJdbcUrl());
        config.setUsername(container.getUsername());
        config.setPassword(container.getPassword());
        config.setDriverClassName(container.getDriverClassName());
        dataSource = new HikariDataSource(config);
    }
}
