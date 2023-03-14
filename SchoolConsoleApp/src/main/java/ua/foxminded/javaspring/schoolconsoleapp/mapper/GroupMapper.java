package ua.foxminded.javaspring.schoolconsoleapp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Group(rs.getInt("group_id"), rs.getString("group_name"));
    }
}
