package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

@ActiveProfiles("Hibernate")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CourseDaoJPA.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/SQL/afterEach1.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseDaoJPATest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CourseDao courseDao;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.add(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Test
    void add_whenInputParamCourse() {
        Course course = new Course("History", "History");
        courseDao.add(course);

        assertEquals(course, em.find(Course.class, 1));
    }

    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.addAll(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Test
    void addAll_whenInputParamLisyOfCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("History", "History"));
        courses.add(new Course("Mathematics", "Mathematics"));
        courses.add(new Course("Biology", "Biology"));

        courseDao.addAll(courses);

        String sql = "SELECT c FROM Course c";
        assertEquals(courses, em.getEntityManager().createQuery(sql, Course.class).getResultList());
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/data1.sql")
    @Test
    void getAll_shouldReturnListOfCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "History", "History"));
        courses.add(new Course(2, "Mathematics", "Mathematics"));
        courses.add(new Course(3, "Biology", "Biology"));

        assertEquals(courses, courseDao.getAll());
    }

    @Test
    void isEmpty_shouldReturnTrue_whenTableIsEmpty() {
        assertEquals(true, courseDao.isEmpty());
    }

    @Sql("/SQL/data1.sql")
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        assertEquals(false, courseDao.isEmpty());
    }
}
