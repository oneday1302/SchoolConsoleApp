package ua.foxminded.javaspring.schoolconsoleapp.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(rs.getInt("course_id"), rs.getString("course_name"), rs.getString("course_description"));
    }
}
