package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.annotation.DaoTest;
import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;
import ua.foxminded.javaspring.schoolconsoleapp.mapper.StudentMapper;

@ActiveProfiles({ "nativeJDBC", "JDBCTemplate", "Hibernate", "DataJPA" })
@SpringBootTest(classes = DaoTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentDaoTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    List<StudentDao> impls;

    Stream<StudentDao> impls() {
        return impls.stream();
    }

    @DaoTest
    void add_shouldReturnIllegalArgumentException_whenInputParamNull(StudentDao studentDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            studentDao.add(null);
        });
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @DaoTest
    void add__whenInputParamStudent(StudentDao studentDao) {
        Student student = new Student(1, "Jacob", "Smith");
        studentDao.add(student);

        String sql = "SELECT * FROM school.students";
        assertEquals(student, jdbc.query(sql, new StudentMapper()).get(0));
    }
    
    @DaoTest
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull(StudentDao studentDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            studentDao.addAll(null);
        });
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @DaoTest
    void addAll__whenInputParamListOfStudent(StudentDao studentDao) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));
        
        studentDao.addAll(students);

        String sql = "SELECT * FROM school.students";
        assertEquals(students, jdbc.query(sql, new StudentMapper()));
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data5.sql")
    @DaoTest
    void getAll_shouldReturnListOfStudents(StudentDao studentDao) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        assertEquals(students, studentDao.getAll());
    }

    @DaoTest
    void updateGroupIdRow_shouldReturnIllegalArgumentException_whenInputNull(StudentDao studentDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            studentDao.updateGroupIdRow(null);
        });
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data6.sql")
    @DaoTest
    void updateGroupIdRow_whenInputStudent(StudentDao studentDao) {
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

    @DaoTest
    void findAllStudentsInTheCourse_shouldReturnIllegalArgumentException_whenInputNull(StudentDao studentDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            studentDao.findAllStudentsInTheCourse(null);
        });
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data7.sql")
    @DaoTest
    void findAllStudentsInTheCourse_shouldReturnListOfStudents_whenInputCourseName(StudentDao studentDao) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Jacob", "Smith"));
        students.add(new Student(2, "Emily", "Jones"));
        students.add(new Student(3, "Michael", "Taylor"));

        assertEquals(students, studentDao.findAllStudentsInTheCourse("History"));
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data8.sql")
    @DaoTest
    void delete_whenInputStudentId(StudentDao studentDao) {
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

    @DaoTest
    void addStudentToCourse_shouldReturnIllegalArgumentException_whenInputNull(StudentDao studentDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            studentDao.addStudentToCourse(null);
        });
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @DaoTest
    void addStudentToCourse_whenInputStudent(StudentDao studentDao) {
        Course course = new Course(1, "History", "History");
        Student student = new Student(1, "Jacob", "Smith");
        student.addCourse(course);

        studentDao.addStudentToCourse(student);
        
        String sql = "SELECT COUNT (*) FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(1, actual);
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @DaoTest
    void removeStudentFromCourses_whenInputStudentId(StudentDao studentDao) {
        studentDao.removeStudentFromCourses(1);

        String sql = "SELECT COUNT (*) FROM school.students_courses";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(0, actual);
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @DaoTest
    void addStudentToCourse_whenInputStudentIdAndCourseId(StudentDao studentDao) {
        studentDao.addStudentToCourse(1, 1);

        String sql = "SELECT COUNT (*) FROM school.students_courses WHERE student_id = 1 AND course_id = 1";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(1, actual);
    }

    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @DaoTest
    void removeStudentFromCourse_whenInputStudentIdAndCourseId(StudentDao studentDao) {
        studentDao.removeStudentFromCourse(1, 1);

        String sql = "SELECT COUNT (*) FROM school.students_courses";
        int actual = jdbc.queryForObject(sql, Integer.class);
        assertEquals(0, actual);
    }
    
    @Sql("/SQL/afterEach3.sql")
    @DaoTest
    void gisEmpty_shouldReturnTrue_whenTableIsEmpty(StudentDao studentDao) {
        assertEquals(true, studentDao.isEmpty());
    }
    
    @Sql("/SQL/afterEach3.sql")
    @Sql("/SQL/data5.sql")
    @DaoTest
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty(StudentDao studentDao) {
        assertEquals(false, studentDao.isEmpty());
    }
}
