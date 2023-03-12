package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import javax.sql.DataSource;
import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

@SpringBootTest(classes = DaoTestConfig.class)
@ActiveProfiles("nativeJDBC")
class CourseDaoImplTest {
    
    @Autowired
    CourseDao courseDao;
    
    @Autowired
    DataSource dataSource;

    @AfterEach
    void cleanup() {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("TRUNCATE TABLE school.courses CASCADE");
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void CourseDaoImpl_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CourseDaoImpl(null);
        });
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.add(null);
        });
    }

    @Test
    void add_whenInputParamCourse() {
        Course course = new Course("History", "History");
        courseDao.add(course);

        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT course_name, course_description FROM school.courses";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                courses.add(new Course(result.getString("course_name"), result.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(course, courses.get(0));
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.addAll(null);
        });
    }
    
    @Test
    void addAll_whenInputParamListOfCourse() {
        List<Course> expected = new ArrayList<>();
        expected.add(new Course("History", "History"));
        expected.add(new Course("Mathematics", "Mathematics"));
        expected.add(new Course("Biology", "Biology"));
        
        courseDao.addAll(expected);

        List<Course> actual = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT course_name, course_description FROM school.courses";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                actual.add(new Course(result.getString("course_name"), result.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expected, actual);
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
