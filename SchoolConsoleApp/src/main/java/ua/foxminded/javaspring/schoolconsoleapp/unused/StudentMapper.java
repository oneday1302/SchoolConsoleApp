package ua.foxminded.javaspring.schoolconsoleapp.unused;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name"));
        if (rs.getInt("group_id") != 0) {
            student.setGroup(new Group(rs.getInt("group_id"), rs.getString("group_name")));
        }
        return student;
    }
}
