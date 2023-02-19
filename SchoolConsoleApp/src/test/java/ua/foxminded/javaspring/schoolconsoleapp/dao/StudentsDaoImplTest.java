package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@SpringBootTest(classes = DaoTestConfig.class)
@ActiveProfiles("nativeJDBC")
class StudentsDaoImplTest {
    
    @Autowired
    StudentDao studentsDao;
    
    @Autowired
    DataSource dataSource;

    @AfterEach
    void cleanup() {
        try (Connection con = dataSource.getConnection()) {
            StringJoiner sql = new StringJoiner(" ");
            sql.add("TRUNCATE TABLE school.students CASCADE;")
               .add("TRUNCATE TABLE school.courses CASCADE;")
               .add("TRUNCATE TABLE school.groups CASCADE;")
               .add("TRUNCATE TABLE school.students_courses CASCADE;");
            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void StudentsDaoImpl_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentDaoImpl(null);
        });
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDao.add(null);
        });
    }

    @Test
    void add__whenInputParamStudent() {
        Student student = new Student("Jacob", "Smith");
        studentsDao.add(student);

        List<Student> students = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT first_name, last_name FROM school.students");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                students.add(new Student(result.getString("first_name"), result.getString("last_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(student, students.get(0));
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDao.addAll(null);
        });
    }

    @Test
    void addAll__whenInputParamListOfStudent() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student("Jacob", "Smith"));
        expected.add(new Student("Emily", "Jones"));
        expected.add(new Student("Michael", "Taylor"));
        studentsDao.addAll(expected);

        List<Student> actual = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT first_name, last_name FROM school.students");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                actual.add(new Student(result.getString("first_name"), result.getString("last_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data5.sql")
    @Test
    void getAll_shouldReturnListOfStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        assertEquals(students, studentsDao.getAll());
    }

    @Test
    void updateGroupIdRow_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDao.updateGroupIdRow(null);
        });
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data6.sql")
    @Test
    void updateGroupIdRow_whenInputStudent() {
        Student student = new Student(1, "Jacob", "Smith");
        student.setGroup(new Group(1, "VK-13"));

        studentsDao.updateGroupIdRow(student);

        List<Student> students = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statementStudent = con.prepareStatement("SELECT * FROM school.students");
            ResultSet resultStudent = statementStudent.executeQuery();
            while (resultStudent.next()) {
                Student tempStudent = new Student(
                        resultStudent.getInt("student_id"),resultStudent.getString("first_name"), resultStudent.getString("last_name"));
                String sql = "SELECT * FROM school.groups WHERE group_id = ?";
                PreparedStatement statementGroup = con.prepareStatement(sql);
                statementGroup.setInt(1, resultStudent.getInt("group_id"));
                ResultSet resultGroup = statementGroup.executeQuery();
                while (resultGroup.next()) {
                    tempStudent
                            .setGroup(new Group(resultGroup.getInt("group_id"), resultGroup.getString("group_name")));
                }
                students.add(tempStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(student, students.get(0));
    }

    @Test
    void findAllStudentsInTheCourse_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDao.findAllStudentsInTheCourse(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data7.sql")
    @Test
    void findAllStudentsInTheCourse_shouldReturnListOfStudents_whenInputCourseName() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        assertEquals(students, studentsDao.findAllStudentsInTheCourse("History"));
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data8.sql")
    @Test
    void delete_whenInputIdStudent() {
        studentsDao.delete(1);

        Student actual = null;
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM school.students");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                actual = new Student(result.getString("first_name"), result.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(null, actual);
    }

    @Test
    void addStudentToCourse_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDao.addStudentToCourse(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @Test
    void addStudentToCourse_whenInputStudent() {
        Course course = new Course(1, "History", "History");
        Student student = new Student(1, "Jacob", "Smith");
        student.addCourse(course);

        studentsDao.addStudentToCourse(student);

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            assertEquals(true, result.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourses_whenInputStudentId() {
        studentsDao.removeStudentFromCourses(1);

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.students_courses";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            assertEquals(false, result.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @Test
    void addStudentToCourse_whenInputStudentIdAndCourseId() {
        studentsDao.addStudentToCourse(1, 1);

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            assertEquals(true, result.next());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourse_whenInputStudentIdAndCourseId() {
        studentsDao.removeStudentFromCourse(1, 1);

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.students_courses";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            assertEquals(false, result.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void isEmpty_shouldReturnTrue_whenTableIsEmpty() {
        assertEquals(true, studentsDao.isEmpty());
    }
    
    @Sql("/SQL/data5.sql")
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        assertEquals(false, studentsDao.isEmpty());
    }
}
