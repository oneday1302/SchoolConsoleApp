package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.Course;

public class CoursesDaoImpl implements CoursesDao {
    private static final String PATH = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1996";

    @Override
    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String insert = "INSERT INTO school.courses (course_name, course_description) VALUES ('%s', '%s')";
            statement.executeUpdate(String.format(insert, course.getCourseName(), course.getCourseDescription()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM school.courses");
            while (result.next()) {
                courses.add(new Course(result.getInt("course_id"), result.getString("course_name"),
                        result.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
