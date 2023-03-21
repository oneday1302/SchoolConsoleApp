package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@ActiveProfiles("DataJPA")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = StudentDaoDataJPA.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/SQL/afterEach3.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentDaoDataJPATest {
    
    @Autowired
    private JpaRepository<Student, Integer> repository;
    
    @Autowired
    private StudentDao studentDao;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.add(null);
        });
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Test
    void add__whenInputParamStudent() {
        Student student = new Student("Jacob", "Smith");
        studentDao.add(student);

        assertEquals(student, repository.getOne(1));
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.addAll(null);
        });
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Test
    void addAll__whenInputParamListOfStudent() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        students.add(new Student("Michael", "Taylor"));
        
        studentDao.addAll(students);

        assertEquals(students, repository.findAll());
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
        assertEquals("VK-13", repository.getOne(1).getGroup().getName());
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
        assertThrows(NoSuchElementException.class, () -> {
            repository.findById(1).get();
        });
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
        
        assertEquals(course, repository.getOne(1).getCourses().get(0));
    }
    
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourses_whenInputStudentId() {
        studentDao.removeStudentFromCourses(1);
        assertEquals(0, repository.getOne(1).getCourses().size());
    }
    
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourse_whenInputStudentIdAndCourseId() {
        studentDao.removeStudentFromCourse(1, 1);
        assertEquals(0, repository.getOne(1).getCourses().size());
    }
    
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @Test
    void addStudentToCourse_whenInputStudentIdAndCourseId() {
        studentDao.addStudentToCourse(1, 1);
        assertEquals(1, repository.getOne(1).getCourses().size());
    }
    
    @Test
    void gisEmpty_shouldReturnTrue_whenTableIsEmpty() {
        assertEquals(true, studentDao.isEmpty());
    }
    
    @Sql("/SQL/data5.sql")
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        assertEquals(false, studentDao.isEmpty());
    }
}
