package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.Group;

public class GroupDaoImpl implements GroupDao {
    private static final String PATH = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1996";

    @Override
    public void addGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String insert = "INSERT INTO school.groups (group_name) VALUES ('%s')";
            statement.executeUpdate(String.format(insert, group.getGroupName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAllGroup() {
        List<Group> groups = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM school.groups");
            while (result.next()) {
                groups.add(new Group(result.getInt("group_id"), result.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    public Group getGroupById(int id) {
        try (Connection con = DriverManager.getConnection(PATH, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            String sqlFormat = "SELECT * FROM school.groups WHERE group_id = %d";
            ResultSet result = statement.executeQuery(String.format(sqlFormat, id));
            while (result.next()) {
                return new Group(result.getInt("group_id"), result.getString("group_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
