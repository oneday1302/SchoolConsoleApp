package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.sql.DataSource;
import ua.foxminded.javaspring.schoolconsoleapp.Group;

public class GroupDaoImpl implements GroupDao {
    private final DataSource dataSource;

    public GroupDaoImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.groups (group_name) VALUES (?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, group.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM school.groups");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                groups.add(new Group(result.getInt("group_id"), result.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public Group get(int id) {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT * FROM school.groups WHERE group_id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return new Group(result.getInt("group_id"), result.getString("group_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        List<Group> groups = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            StringJoiner sql = new StringJoiner(" ");
            sql.add("SELECT school.groups.group_id, group_name FROM school.groups")
               .add("JOIN school.students ON school.groups.group_id = school.students.group_id")
               .add("GROUP BY school.groups.group_id HAVING COUNT(school.students.student_id) <= ?");
            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.setInt(1, studentsNumber);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                groups.add(new Group(result.getInt("group_id"), result.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }
}
