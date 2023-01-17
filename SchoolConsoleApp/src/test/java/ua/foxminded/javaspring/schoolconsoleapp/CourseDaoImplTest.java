package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.sql.DataSource;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDaoImpl;

class CourseDaoImplTest extends IntegrationTestBase {

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
        DataSource mocDataSource = Mockito.mock(DataSource.class);
        CourseDaoImpl courseDaoImpl = new CourseDaoImpl(mocDataSource);
        assertThrows(IllegalArgumentException.class, () -> {
            courseDaoImpl.add(null);
        });
    }

    @Test
    void add_whenInputParamCourse() {
        Course course = new Course("History", "History");
        CourseDaoImpl courseDaoImpl = new CourseDaoImpl(dataSource);
        courseDaoImpl.add(course);

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
    void getAll_shouldReturnListOfCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(2, "History", "History"));
        courses.add(new Course(3, "Mathematics", "Mathematics"));
        courses.add(new Course(4, "Biology", "Biology"));

        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            for (Course course : courses) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getDesc());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        CourseDaoImpl courseDaoImpl = new CourseDaoImpl(dataSource);
        assertEquals(courses, courseDaoImpl.getAll());
    }
}
