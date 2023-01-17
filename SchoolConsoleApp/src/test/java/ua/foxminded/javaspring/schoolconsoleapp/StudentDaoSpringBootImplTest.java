package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDaoSpringBootImpl;

class StudentDaoSpringBootImplTest extends IntegrationTestBase {
    
    @Autowired
    StudentDaoSpringBootImpl studentDao;

    @AfterEach
    void cleanup() {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("TRUNCATE TABLE school.students CASCADE;")
           .add("TRUNCATE TABLE school.courses CASCADE;")
           .add("TRUNCATE TABLE school.groups CASCADE;")
           .add("TRUNCATE TABLE school.students_courses CASCADE;");
        jdbc.update(sql.toString());
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.add(null);
        });
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Test
    void add__whenInputParamStudent() {
        Student student = new Student(1, "Jacob", "Smith");
        studentDao.add(student);

        String sql = "SELECT * FROM school.students";
        assertEquals(student, jdbc.query(sql, new StudentMapper()).get(0));
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data5.sql")
    @Test
    void getAll_shouldReturnListOfStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        assertEquals(students, studentDao.getAll());
    }

    @Test
    void updateGroupIdRow_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.updateGroupIdRow(null);
        });
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data6.sql")
    @Test
    void updateGroupIdRow_whenInputStudent() {
        Student student = new Student(1, "Jacob", "Smith");
        student.setGroup(new Group(1, "VK-13"));

        studentDao.updateGroupIdRow(student);

        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT school.students.student_id, school.students.group_id, school.groups.group_name, first_name, last_name")
           .add("FROM school.students")
           .add("LEFT JOIN school.groups")
           .add("ON school.students.group_id = school.groups.group_id");
        assertEquals(student, jdbc.query(sql.toString(), new StudentMapper()).get(0));
    }

    @Test
    void findAllStudentsInTheCourse_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.findAllStudentsInTheCourse(null);
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

        assertEquals(students, studentDao.findAllStudentsInTheCourse("History"));
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data8.sql")
    @Test
    void delete_whenInputStudentId() {
        studentDao.delete(1);

        Student student = null;
        try {
            String sql = "SELECT * FROM school.students";
            student = jdbc.queryForObject(sql, new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            student = null;
        }
        assertEquals(null, student);
    }

    @Test
    void addStudentToCourse_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.addStudentToCourse(null);
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

        studentDao.addStudentToCourse(student);
        
        String sql = "SELECT COUNT (*) FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(1, actual);
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourses_whenInputStudentId() {
        studentDao.removeStudentFromCourses(1);

        String sql = "SELECT COUNT (*) FROM school.students_courses";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(0, actual);
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @Test
    void addStudentToCourse_whenInputStudentIdAndCourseId() {
        studentDao.addStudentToCourse(1, 1);

        String sql = "SELECT COUNT (*) FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(1, actual);
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourse_whenInputStudentIdAndCourseId() {
        studentDao.removeStudentFromCourse(1, 1);

        String sql = "SELECT COUNT (*) FROM school.students_courses";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(0, actual);
    }
}
