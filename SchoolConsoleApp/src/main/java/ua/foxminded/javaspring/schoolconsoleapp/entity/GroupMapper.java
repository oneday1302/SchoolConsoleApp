package ua.foxminded.javaspring.schoolconsoleapp.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Group(rs.getInt("group_id"), rs.getString("group_name"));
    }
}
