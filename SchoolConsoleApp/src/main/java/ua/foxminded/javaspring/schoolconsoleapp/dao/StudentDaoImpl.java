package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Repository
@Profile("nativeJDBC")
public class StudentDaoImpl implements StudentDao {
    private final DataSource dataSource;

    public StudentDaoImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAll(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            for (Student student : students) {
                statement.setString(1, student.getFirstName());
                statement.setString(2, student.getLastName());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            StringJoiner sql = new StringJoiner(" ");
            sql.add("SELECT school.students.student_id, school.students.group_id, school.groups.group_name, first_name, last_name")
               .add("FROM school.students")
               .add("LEFT JOIN school.groups")
               .add("ON school.students.group_id = school.groups.group_id")
               .add("ORDER BY school.students.student_id");
            PreparedStatement statement = con.prepareStatement(sql.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Student student = new Student(result.getInt("student_id"), result.getString("first_name"), result.getString("last_name"));
                if (result.getInt("group_id") != 0) {
                    student.setGroup(new Group(result.getInt("group_id"), result.getString("group_name")));
                }
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
        try (Connection con = dataSource.getConnection()) {
            String sql = "UPDATE school.students SET group_id = ? WHERE student_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            if (student.getGroup() == null) {
                statement.setNull(1, java.sql.Types.INTEGER);
            } else {
                statement.setInt(1, student.getGroup().getId());
            }
            statement.setInt(2, student.getId());
            statement.execute();
            
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
        try (Connection con = dataSource.getConnection()) {
            StringJoiner sql = new StringJoiner(" ");
            sql.add("SELECT school.students.student_id, school.students.group_id, school.groups.group_name, first_name, last_name")
               .add("FROM school.students")
               .add("LEFT JOIN school.groups")
               .add("ON school.students.group_id = school.groups.group_id")
               .add("JOIN school.students_courses")
               .add("ON school.students.student_id = school.students_courses.student_id")
               .add("JOIN school.courses")
               .add("ON school.students_courses.course_id = school.courses.course_id")
               .add("WHERE school.courses.course_name = ?");
            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setString(1, courseName);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Student student = new Student(result.getInt("student_id"), result.getString("first_name"), result.getString("last_name"));
                if (result.getInt("group_id") != 0) {
                    student.setGroup(new Group(result.getInt("group_id"), result.getString("group_name")));
                }
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void delete(int id) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM school.students WHERE student_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStudentToCourse(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            for (Course course : student.getCourses()) {
                statement.setInt(1, student.getId());
                statement.setInt(2, course.getId());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM school.students_courses WHERE student_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmpty() {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT student_id FROM school.students LIMIT 1");
            ResultSet result = statement.executeQuery();
            return !result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
