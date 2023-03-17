package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.mapper.CourseMapper;

@Repository
@Profile("JDBCTemplate")
public class CourseDaoJDBC implements CourseDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        String sql = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
        jdbc.update(sql, course.getName(), course.getDescription());
    }

    @Override
    public void addAll(List<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        String sql = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, courses.get(i).getName());
                ps.setString(2, courses.get(i).getDescription());
            }

            public int getBatchSize() {
                return courses.size();
            }
        });
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
        return count == 0;
    }
}
