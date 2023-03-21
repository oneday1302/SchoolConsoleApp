package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.annotation.DaoTest;
import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.mapper.CourseMapper;

@ActiveProfiles({ "nativeJDBC", "JDBCTemplate", "Hibernate", "DataJPA" })
@SpringBootTest(classes = DaoTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseDaoTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    List<CourseDao> impls;

    Stream<CourseDao> impls() {
        return impls.stream();
    }

    @DaoTest
    void add_shouldReturnIllegalArgumentException_whenInputParamNull(CourseDao courseDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            courseDao.add(null);
        });
    }

    @Sql("/SQL/afterEach1.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @DaoTest
    void add_whenInputParamCourse(CourseDao courseDao) {
        Course course = new Course(1, "History", "History");
        courseDao.add(course);
        String sql = "SELECT * FROM school.courses";
        assertEquals(course, jdbc.query(sql, new CourseMapper()).get(0));
    }

    @DaoTest
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull(CourseDao courseDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            courseDao.addAll(null);
        });
    }

    @Sql("/SQL/afterEach1.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @DaoTest
    void addAll_whenInputParamLisyOfCourse(CourseDao courseDao) {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "History", "History"));
        courses.add(new Course(2, "Mathematics", "Mathematics"));
        courses.add(new Course(3, "Biology", "Biology"));

        courseDao.addAll(courses);

        String sql = "SELECT * FROM school.courses";
        assertEquals(courses, jdbc.query(sql, new CourseMapper()));
    }

    @Sql("/SQL/afterEach1.sql")
    @Sql("/SQL/setCourseSetval.sql")
    @Sql("/SQL/data1.sql")
    @DaoTest
    void getAll_shouldReturnListOfCourses(CourseDao courseDao) {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "History", "History"));
        courses.add(new Course(2, "Mathematics", "Mathematics"));
        courses.add(new Course(3, "Biology", "Biology"));

        assertEquals(courses, courseDao.getAll());
    }

    @Sql("/SQL/afterEach1.sql")
    @DaoTest
    void isEmpty_shouldReturnTrue_whenTableIsEmpty(CourseDao courseDao) {
        assertEquals(true, courseDao.isEmpty());
    }

    @Sql("/SQL/afterEach1.sql")
    @Sql("/SQL/data1.sql")
    @DaoTest
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty(CourseDao courseDao) {
        assertEquals(false, courseDao.isEmpty());
    }
}
