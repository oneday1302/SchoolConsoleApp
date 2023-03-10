package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseMapper;

@SpringBootTest(classes = DaoTestConfig.class)
@ActiveProfiles("JDBCTemplate")
class CourseDaoJDBCTest {
    
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    CourseDao<Course> courseDao;
    
    @AfterEach
    void cleanup() {
        jdbc.update("TRUNCATE TABLE school.courses CASCADE");
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.add(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Test
    void add_whenInputParamCourse() {
        Course course = new Course(1, "History", "History");
        courseDao.add(course);

        String sql = "SELECT * FROM school.courses";
        assertEquals(course, jdbc.query(sql, new CourseMapper()).get(0));
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.addAll(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Test
    void addAll_whenInputParamLisyOfCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "History", "History"));
        courses.add(new Course(2, "Mathematics", "Mathematics"));
        courses.add(new Course(3, "Biology", "Biology"));
        
        courseDao.addAll(courses);

        String sql = "SELECT * FROM school.courses";
        assertEquals(courses, jdbc.query(sql, new CourseMapper()));
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/data1.sql")
    @Test
    void getAll_shouldReturnListOfCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "History", "History"));
        courses.add(new Course(2, "Mathematics", "Mathematics"));
        courses.add(new Course(3, "Biology", "Biology"));

        assertEquals(courses, courseDao.getAll());
    }
    
    @Test
    void isEmpty_shouldReturnTrue_whenTableIsEmpty() {
        assertEquals(true, courseDao.isEmpty());
    }
    
    @Sql("/SQL/data1.sql")
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        assertEquals(false, courseDao.isEmpty());
    }
}
