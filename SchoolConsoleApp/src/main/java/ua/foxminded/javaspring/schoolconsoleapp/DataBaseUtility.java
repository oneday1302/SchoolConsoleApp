package ua.foxminded.javaspring.schoolconsoleapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

public class DataBaseUtility extends BasicDataSource {

    public DataBaseUtility(String propertyFileName) {
        if (propertyFileName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        try (InputStream input = this.getClass().getResourceAsStream(propertyFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            
            this.setDriverClassName(prop.getProperty("driverName"));
            this.setUrl(prop.getProperty("url"));
            this.setUsername(prop.getProperty("user"));
            this.setPassword(prop.getProperty("password"));

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
