package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import ua.foxminded.javaspring.schoolconsoleapp.Student;

public class StudentsDaoImpl implements StudentsDao {
    private static final String PATH = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1996";

    @Override
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "INSERT INTO school.students (first_name, last_name) VALUES ('%s', '%s')";
            statement.executeUpdate(String.format(sqlFormat, student.getFirstName(), student.getLastName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            GroupDao groupsDao = new GroupDaoImpl();
            ResultSet result = statement.executeQuery("SELECT * FROM school.students");
            while (result.next()) {
                Student student = new Student(result.getInt("student_id"), result.getString("first_name"), result.getString("last_name"));
                student.setGroup(groupsDao.getGroupById(result.getInt("group_id")));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void updateGroupIdRow(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        if (student.getGroup() == null) {
            return;
        }
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "UPDATE school.students SET group_id = %d WHERE student_id = %d";
            statement.executeUpdate(String.format(sqlFormat, student.getGroup().getGroupID(), student.getStudentID()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        List<Student> students = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            GroupDao groupsDao = new GroupDaoImpl();
            StringJoiner sqlFormat = new StringJoiner(" ");
            sqlFormat.add("SELECT * FROM school.students").add("JOIN school.students_courses")
                     .add("ON school.students.student_id = school.students_courses.student_id")
                     .add("JOIN school.courses").add("ON school.students_courses.course_id = school.courses.course_id")
                     .add("WHERE school.courses.course_name = '%s'");
            ResultSet result = statement.executeQuery(String.format(sqlFormat.toString(), courseName));
            while (result.next()) {
                Student student = new Student(result.getInt("student_id"), result.getString("first_name"), result.getString("last_name"));
                student.setGroup(groupsDao.getGroupById(result.getInt("group_id")));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void deleteStudentById(int id) {
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "DELETE FROM school.students WHERE student_id = %d";
            StudentsCoursesDao studentsCoursesDao = new StudentsCoursesDaoImpl();
            studentsCoursesDao.removeStudentFromCourse(id);
            statement.executeUpdate(String.format(sqlFormat, id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
