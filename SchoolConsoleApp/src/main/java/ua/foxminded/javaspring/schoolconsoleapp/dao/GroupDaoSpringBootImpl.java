package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.Group;
import ua.foxminded.javaspring.schoolconsoleapp.GroupMapper;

@Repository
public class GroupDaoSpringBootImpl implements GroupDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        String sql = "INSERT INTO school.groups (group_name) VALUES (?)";
        jdbc.update(sql, group.getName());
    }

    @Override
    public List<Group> getAll() {
        String sql = "SELECT * FROM school.groups ORDER BY group_id";
        return jdbc.query(sql, new GroupMapper());
    }

    @Override
    public Group get(int id) {
        try {
            String sql = "SELECT * FROM school.groups WHERE group_id = ?";
            return jdbc.queryForObject(sql, new Object[] { id }, new GroupMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT school.groups.group_id, group_name FROM school.groups")
           .add("JOIN school.students ON school.groups.group_id = school.students.group_id")
           .add("GROUP BY school.groups.group_id HAVING COUNT(school.students.student_id) <= ?")
           .add("ORDER BY group_id");
        return jdbc.query(sql.toString(), new Object[] { studentsNumber }, new GroupMapper());
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(group_id) FROM school.groups";
        int count = jdbc.queryForObject(sql, Integer.class);
        if (count == 0) {
            return true;
        }
        return false;
    }
}