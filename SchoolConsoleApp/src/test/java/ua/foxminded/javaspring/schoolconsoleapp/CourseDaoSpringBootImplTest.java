package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDaoSpringBootImpl;

class CourseDaoSpringBootImplTest extends IntegrationTestBase {

    @Autowired
    CourseDaoSpringBootImpl courseDao;
    
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
}
