package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import ua.foxminded.javaspring.schoolconsoleapp.Course;
import ua.foxminded.javaspring.schoolconsoleapp.Student;

public class StudentsCoursesDaoImpl implements StudentsCoursesDao {
    private static final String PATH = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1996";

    @Override
    public void addStudentsToCourses(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String insertFormat = "INSERT INTO school.students_courses (student_id, course_id) VALUES (%d, %d)";
            for (Course course : student.getCourses()) {
                statement.executeUpdate(String.format(insertFormat, student.getStudentID(), course.getCourseID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudentFromCourse(int studentId) {
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "DELETE FROM school.students_courses WHERE student_id = %d";
            statement.executeUpdate(String.format(sqlFormat, studentId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "INSERT INTO school.students_courses (student_id, course_id) VALUES (%d, %d) ON CONFLICT DO NOTHING";
            statement.executeUpdate(String.format(sqlFormat, studentId, courseId, studentId, courseId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "DELETE FROM school.students_courses WHERE student_id = %d AND course_id = %d";
            statement.executeUpdate(String.format(sqlFormat, studentId, courseId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
