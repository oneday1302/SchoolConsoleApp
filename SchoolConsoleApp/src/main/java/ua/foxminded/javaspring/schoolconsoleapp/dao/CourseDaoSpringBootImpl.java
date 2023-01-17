package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.Course;
import ua.foxminded.javaspring.schoolconsoleapp.CourseMapper;

@Repository
public class CourseDaoSpringBootImpl implements CourseDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        String sql = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
        jdbc.update(sql, course.getName(), course.getDesc());
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM school.courses ORDER BY course_id";
        return jdbc.query(sql, new CourseMapper());
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(course_id) FROM school.courses";
        int count = jdbc.queryForObject(sql, Integer.class);
        if (count == 0) {
            return true;
        }
        return false;
    }
}
