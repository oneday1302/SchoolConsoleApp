package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;

@ActiveProfiles("DataJPA")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CourseDaoJPA.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/SQL/afterEach1.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CourseDaoJPATest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CourseDao<CourseEntity> courseDao;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            courseDao.add(null);
        });
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Test
    void add_whenInputParamCourse() {
        CourseEntity course = new CourseEntity("History", "History");
        courseDao.add(course);

        String sql = "SELECT c FROM CourseEntity c";
        assertEquals(course, em.createQuery(sql, CourseEntity.class).getSingleResult());
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
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(new CourseEntity("History", "History"));
        courses.add(new CourseEntity("Mathematics", "Mathematics"));
        courses.add(new CourseEntity("Biology", "Biology"));

        courseDao.addAll(courses);

        String sql = "SELECT c FROM CourseEntity c";
        assertEquals(courses, em.createQuery(sql, CourseEntity.class).getResultList());
    }

    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/data1.sql")
    @Test
    void getAll_shouldReturnListOfCourses() {
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(new CourseEntity(1, "History", "History"));
        courses.add(new CourseEntity(2, "Mathematics", "Mathematics"));
        courses.add(new CourseEntity(3, "Biology", "Biology"));

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
