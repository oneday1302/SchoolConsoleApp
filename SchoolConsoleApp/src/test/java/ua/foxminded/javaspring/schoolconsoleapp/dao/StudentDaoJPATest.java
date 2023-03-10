package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

@ActiveProfiles("DataJPA")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = StudentDaoJPA.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/SQL/afterEach3.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentDaoJPATest {

    @Autowired
    private EntityManager em;
    
    @Autowired
    StudentDao<StudentEntity> studentDao;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentDao.add(null);
        });
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Test
    void add__whenInputParamStudent() {
        StudentEntity student = new StudentEntity("Jacob", "Smith");
        studentDao.add(student);

        String sql = "SELECT s FROM StudentEntity s";
        assertEquals(student, em.createQuery(sql, StudentEntity.class).getSingleResult());
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
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity("Jacob", "Smith"));
        students.add(new StudentEntity("Emily", "Jones"));
        students.add(new StudentEntity("Michael", "Taylor"));
        
        studentDao.addAll(students);

        String sql = "SELECT s FROM StudentEntity s";
        assertEquals(students, em.createQuery(sql, StudentEntity.class).getResultList());
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data5.sql")
    @Test
    void getAll_shouldReturnListOfStudents() {
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity(1, "Jacob", "Smith"));
        students.add(new StudentEntity(2, "Emily", "Jones"));
        students.add(new StudentEntity(3, "Michael", "Taylor"));

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
        StudentEntity student = new StudentEntity(1, "Jacob", "Smith");
        student.setGroup(new GroupEntity(1, "VK-13"));

        studentDao.updateGroupIdRow(student);

        String sql = "SELECT s FROM StudentEntity s";
        assertEquals(student, em.createQuery(sql.toString(), StudentEntity.class).getSingleResult());
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
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity(1, "Jacob", "Smith"));
        students.add(new StudentEntity(2, "Emily", "Jones"));
        students.add(new StudentEntity(3, "Michael", "Taylor"));

        assertEquals(students, studentDao.findAllStudentsInTheCourse("History"));
    }

    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data8.sql")
    @Test
    void delete_whenInputStudentId() {
        studentDao.delete(1);

        String sql = "SELECT s FROM StudentEntity s";
        assertThrows(NoResultException.class, () -> {
            em.createQuery(sql, StudentEntity.class).getSingleResult();
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
        CourseEntity course = new CourseEntity(1, "History", "History");
        StudentEntity student = new StudentEntity("Jacob", "Smith");
        student.addCourse(course);

        studentDao.addStudentToCourse(student);
        
        String sql = "SELECT c FROM StudentEntity s Join s.courses c where c.name = ?1";
        assertEquals(course, em.createQuery(sql, CourseEntity.class).setParameter(1, course.getName()).getSingleResult());
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourses_whenInputStudentId() {
        studentDao.removeStudentFromCourses(1);
        String sql = "select count (c) from StudentEntity s join s.courses c";
        assertEquals(0, em.createQuery(sql, Long.class).getSingleResult());
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data9.sql")
    @Test
    void addStudentToCourse_whenInputStudentIdAndCourseId() {
        studentDao.addStudentToCourse(1, 1);
        String sql = "select count (c) from StudentEntity s join s.courses c";
        assertEquals(1, em.createQuery(sql, Long.class).getSingleResult());
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/data10.sql")
    @Test
    void removeStudentFromCourse_whenInputStudentIdAndCourseId() {
        studentDao.removeStudentFromCourse(1, 1);
        String sql = "select count (c) from StudentEntity s join s.courses c";
        assertEquals(0, em.createQuery(sql, Long.class).getSingleResult());
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
