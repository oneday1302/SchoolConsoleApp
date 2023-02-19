package ua.foxminded.javaspring.schoolconsoleapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.foxminded.javaspring.schoolconsoleapp.configs.ServiceLayerTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.service.CourseService;

@SpringBootTest(classes = ServiceLayerTestConfig.class)
class CourseServiceImplTest {
    
    @Autowired
    CourseDao courseDao;
    
    @Autowired
    CourseService courseService;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.add(null);
        });
    }
    
    @Test
    void add_whenInputParamCourse() {
        Course course = new Course("History", "History");
        courseService.add(course);
        verify(courseDao, times(1)).add(course);
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseService.addAll(null);
        });
    }
    
    @Test
    void addAll_whenInputParamListOfCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("History", "History"));
        courses.add(new Course("Biology", "Biology"));
        courseService.addAll(courses);
        verify(courseDao, times(1)).addAll(courses);
    }
    
    @Test
    void getAll_shouldReturnListOfCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("History", "History"));
        courses.add(new Course("Biology", "Biology"));
        when(courseDao.getAll()).thenReturn(courses);
        assertEquals(courses, courseService.getAll());
    }
    
    @Test
    void isEmpty_shouldReturnTrue_whenTableIsEmpty() {
        when(courseDao.isEmpty()).thenReturn(true);
        assertEquals(true, courseService.isEmpty());
    }
    
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        when(courseDao.isEmpty()).thenReturn(false);
        assertEquals(false, courseService.isEmpty());
    }
}
