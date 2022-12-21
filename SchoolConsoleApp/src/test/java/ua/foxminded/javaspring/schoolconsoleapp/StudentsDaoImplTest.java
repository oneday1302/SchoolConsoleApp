package ua.foxminded.javaspring.schoolconsoleapp;

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
import org.mockito.Mockito;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;

class StudentsDaoImplTest extends MyContainer {

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
            new StudentsDaoImpl(null);
        });
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        DataSource mocDataSource = Mockito.mock(DataSource.class);
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(mocDataSource);
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDaoImpl.add(null);
        });
    }

    @Test
    void add__whenInputParamStudent() {
        Student student = new Student("Jacob", "Smith");
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.add(student);

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
    void getAll_shouldReturnListOfStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        try (Connection con = dataSource.getConnection()) {
            String sqlSetval = "SELECT setval('school.students_student_id_seq', 1, false)";
            PreparedStatement statementSetval = con.prepareStatement(sqlSetval);
            statementSetval.execute();

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

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        assertEquals(students, studentsDaoImpl.getAll());
    }

    @Test
    void updateGroupIdRow_shouldReturnIllegalArgumentException_whenInputNull() {
        DataSource mocDataSource = Mockito.mock(DataSource.class);
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(mocDataSource);
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDaoImpl.updateGroupIdRow(null);
        });
    }

    @Test
    void updateGroupIdRow_whenInputStudent() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "VK-13"));
        groups.add(new Group(2, "AT-01"));

        Student student = new Student(1, "Jacob", "Smith");
        student.setGroup(groups.get(0));

        try (Connection con = dataSource.getConnection()) {
            String sqlGroup = "INSERT INTO school.groups (group_id, group_name) VALUES (?, ?)";
            PreparedStatement statementGroup = con.prepareStatement(sqlGroup);
            for (Group group : groups) {
                statementGroup.setInt(1, group.getId());
                statementGroup.setString(2, group.getName());
                statementGroup.addBatch();
            }
            statementGroup.executeBatch();

            String sqlStudent = "INSERT INTO school.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
            PreparedStatement statementStudent = con.prepareStatement(sqlStudent);
            statementStudent.setInt(1, student.getId());
            statementStudent.setInt(2, student.getGroup().getId());
            statementStudent.setString(3, student.getFirstName());
            statementStudent.setString(4, student.getLastName());
            statementStudent.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        student.setGroup(groups.get(1));
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.updateGroupIdRow(student);

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
        DataSource mocDataSource = Mockito.mock(DataSource.class);
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(mocDataSource);
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDaoImpl.findAllStudentsInTheCourse(null);
        });
    }

    @Test
    void findAllStudentsInTheCourse_shouldReturnListOfStudents_whenInputCourseName() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        Course course = new Course(1, "History", "History");

        try (Connection con = dataSource.getConnection()) {
            StringJoiner sqlSetval = new StringJoiner(" ");
            sqlSetval.add("SELECT setval('school.students_student_id_seq', 1, false);")
                     .add("SELECT setval('school.courses_course_id_seq', 1, false);");
            PreparedStatement statementSetval = con.prepareStatement(sqlSetval.toString());
            statementSetval.execute();

            String sqlCourse = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
            PreparedStatement statCourse = con.prepareStatement(sqlCourse);
            statCourse.setString(1, course.getName());
            statCourse.setString(2, course.getDesc());
            statCourse.execute();

            String sqlStudent = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?)";
            PreparedStatement statStudent = con.prepareStatement(sqlStudent);
            for (Student student : students) {
                statStudent.setString(1, student.getFirstName());
                statStudent.setString(2, student.getLastName());
                statStudent.addBatch();
            }
            statStudent.executeBatch();

            String sqlStudentCourse = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?)";
            PreparedStatement statStudentCourse = con.prepareStatement(sqlStudentCourse);
            for (Student student : students) {
                statStudentCourse.setInt(1, student.getId());
                statStudentCourse.setInt(2, course.getId());
                statStudentCourse.addBatch();
            }
            statStudentCourse.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        assertEquals(students, studentsDaoImpl.findAllStudentsInTheCourse("History"));
    }

    @Test
    void delete_whenInputIdStudent() {
        Student student = new Student(1, "Jacob", "Smith");

        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statementStudent = con.prepareStatement(sql);
            statementStudent.setInt(1, student.getId());
            statementStudent.setString(2, student.getFirstName());
            statementStudent.setString(3, student.getLastName());
            statementStudent.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.delete(1);

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
        DataSource mocDataSource = Mockito.mock(DataSource.class);
        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(mocDataSource);
        assertThrows(IllegalArgumentException.class, () -> {
            studentsDaoImpl.addStudentToCourse(null);
        });
    }

    @Test
    void addStudentToCourse_whenInputStudent() {
        Course course = new Course(1, "History", "History");
        Student student = new Student(1, "Jacob", "Smith");
        student.addCourse(course);

        try (Connection con = dataSource.getConnection()) {
            String sqlCourse = "INSERT INTO school.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
            PreparedStatement statementCourse = con.prepareStatement(sqlCourse);
            statementCourse.setInt(1, course.getId());
            statementCourse.setString(2, course.getName());
            statementCourse.setString(3, course.getDesc());
            statementCourse.execute();

            String sqlStudent = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statementStudent = con.prepareStatement(sqlStudent);
            statementStudent.setInt(1, student.getId());
            statementStudent.setString(2, student.getFirstName());
            statementStudent.setString(3, student.getLastName());
            statementStudent.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.addStudentToCourse(student);

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            assertEquals(true, result.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeStudentFromCourses_whenInputStudentId() {
        Course course = new Course(1, "History", "History");
        Student student = new Student(1, "Jacob", "Smith");

        try (Connection con = dataSource.getConnection()) {
            String sqlCourse = "INSERT INTO school.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
            PreparedStatement statementCourse = con.prepareStatement(sqlCourse);
            statementCourse.setInt(1, course.getId());
            statementCourse.setString(2, course.getName());
            statementCourse.setString(3, course.getDesc());
            statementCourse.execute();

            String sqlStudent = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statementStudent = con.prepareStatement(sqlStudent);
            statementStudent.setInt(1, student.getId());
            statementStudent.setString(2, student.getFirstName());
            statementStudent.setString(3, student.getLastName());
            statementStudent.execute();

            String sqlStudentCourse = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?)";
            PreparedStatement statStudentCourse = con.prepareStatement(sqlStudentCourse);
            statStudentCourse.setInt(1, student.getId());
            statStudentCourse.setInt(2, course.getId());
            statStudentCourse.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.removeStudentFromCourses(1);

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
    void addStudentToCourse_whenInputStudentIdAndCourseId() {
        Course course = new Course(1, "History", "History");
        Student student = new Student(1, "Jacob", "Smith");
        student.addCourse(course);

        try (Connection con = dataSource.getConnection()) {
            String sqlCourse = "INSERT INTO school.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
            PreparedStatement statementCourse = con.prepareStatement(sqlCourse);
            statementCourse.setInt(1, course.getId());
            statementCourse.setString(2, course.getName());
            statementCourse.setString(3, course.getDesc());
            statementCourse.execute();

            String sqlStudent = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statementStudent = con.prepareStatement(sqlStudent);
            statementStudent.setInt(1, student.getId());
            statementStudent.setString(2, student.getFirstName());
            statementStudent.setString(3, student.getLastName());
            statementStudent.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.addStudentToCourse(student.getId(), course.getId());

        Student actual = null;
        try (Connection con = dataSource.getConnection()) {
            StringJoiner sql = new StringJoiner(" ");
            sql.add("SELECT school.students.student_id, first_name, last_name, school.courses.course_id, course_name, course_description FROM school.students")
               .add("JOIN school.students_courses")
               .add("ON school.students.student_id = school.students_courses.student_id")
               .add("JOIN school.courses")
               .add("ON school.students_courses.course_id = school.courses.course_id");
            PreparedStatement statement = con.prepareStatement(sql.toString());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Student tempStudent = new Student(
                        result.getInt("student_id"), result.getString("first_name"),result.getString("last_name"));
                tempStudent.addCourse(
                        new Course(result.getInt("course_id"), result.getString("course_name"),result.getString("course_description")));
                actual = tempStudent;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(student, actual);
    }

    @Test
    void removeStudentFromCourse_whenInputStudentIdAndCourseId() {
        Course course = new Course(1, "History", "History");
        Student student = new Student(1, "Jacob", "Smith");

        try (Connection con = dataSource.getConnection()) {
            String sqlCourse = "INSERT INTO school.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
            PreparedStatement statementCourse = con.prepareStatement(sqlCourse);
            statementCourse.setInt(1, course.getId());
            statementCourse.setString(2, course.getName());
            statementCourse.setString(3, course.getDesc());
            statementCourse.execute();

            String sqlStudent = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?)";
            PreparedStatement statementStudent = con.prepareStatement(sqlStudent);
            statementStudent.setInt(1, student.getId());
            statementStudent.setString(2, student.getFirstName());
            statementStudent.setString(3, student.getLastName());
            statementStudent.execute();

            String sqlStudentCourse = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?)";
            PreparedStatement statStudentCourse = con.prepareStatement(sqlStudentCourse);
            statStudentCourse.setInt(1, student.getId());
            statStudentCourse.setInt(2, course.getId());
            statStudentCourse.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentsDaoImpl studentsDaoImpl = new StudentsDaoImpl(dataSource);
        studentsDaoImpl.removeStudentFromCourse(student.getId(), course.getId());

        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.students_courses";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            assertEquals(false, result.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
