package ua.foxminded.javaspring.schoolconsoleapp.configs;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;

@Configuration
@ComponentScan("ua.foxminded.javaspring.schoolconsoleapp.service")
public class ServiceLayerTestConfig {

    @Bean
    public CourseDao getCourseDao() {
        return Mockito.mock(CourseDao.class);
    }
    
    @Bean
    public GroupDao getGroupDao() {
        return Mockito.mock(GroupDao.class);
    }
    
    @Bean
    public StudentDao getStudentDao() {
        return Mockito.mock(StudentDao.class);
    }
}
