package ua.foxminded.javaspring.schoolconsoleapp.configs;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Configuration
@ComponentScan("ua.foxminded.javaspring.schoolconsoleapp.service")
public class ServiceLayerTestConfig {

    @Bean
    public CourseDao<Course> getCourseDao() {
        return Mockito.mock(CourseDao.class);
    }
    
    @Bean
    public GroupDao<Group> getGroupDao() {
        return Mockito.mock(GroupDao.class);
    }
    
    @Bean
    public StudentDao<Student> getStudentDao() {
        return Mockito.mock(StudentDao.class);
    }
}
