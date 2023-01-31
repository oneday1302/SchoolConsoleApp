package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDaoImpl;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("nativeJDBC")
class GroupDaoImplTest {
    
    @Autowired
    GroupDao groupDao;
    
    @Autowired
    DataSource dataSource;

    @AfterEach
    void cleanup() {
        try (Connection con = dataSource.getConnection()) {
            String sql = "TRUNCATE TABLE school.groups CASCADE; TRUNCATE TABLE school.students CASCADE;";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void GroupDaoImpl_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupDaoImpl(null);
        });
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupDao.add(null);
        });
    }

    @Test
    void add__whenInputParamGroup() {
        Group group = new Group("AT-42");
        groupDao.add(group);

        List<Group> groups = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT group_name FROM school.groups");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                groups.add(new Group(result.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(group, groups.get(0));
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupDao.addAll(null);
        });
    }

    @Test
    void addAll__whenInputParamListOfGroup() {
        List<Group> expected = new ArrayList<>();
        expected.add(new Group("AT-42"));
        expected.add(new Group("VK-13"));
        expected.add(new Group("GG-01"));
        groupDao.addAll(expected);

        List<Group> actual = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT group_name FROM school.groups");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                actual.add(new Group(result.getString("group_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/data2.sql")
    @Test
    void getAll_shouldReturnListOfGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AT-42"));
        groups.add(new Group(2, "VK-13"));
        groups.add(new Group(3, "GG-01"));

        assertEquals(groups, groupDao.getAll());
    }

    @Test
    void get_shouldReturnNull_whenInputIncorrectGroupId() {
        assertEquals(null, groupDao.get(1));
    }
    
    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/data3.sql")
    @Test
    void get_shouldReturnGroup_whenInputGroupId() {
        Group group = new Group(1, "AT-42");        
        assertEquals(group, groupDao.get(1));
    }
    
    @Sql("/SQL/data4.sql")
    @Test
    void getAllGrupsWithLessOrEqualsStudentsNumber_shouldReturnListOfGroups_whenInputStudentsNumber() {
        Group group = new Group(1, "VK-13");
        
        Student student1 = new Student("Jacob", "Smith");
        student1.setGroup(group);
        Student student2 = new Student("Emily", "Jones");
        student2.setGroup(group);
        Student student3 = new Student("Michael", "Taylor");
        student3.setGroup(group);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        
        assertEquals(group, groupDao.getAllGrupsWithLessOrEqualsStudentsNumber(3).get(0));
    }
    
    @Test
    void isEmpty_shouldReturnTrue_whenTableIsEmpty() {
        assertEquals(true, groupDao.isEmpty());
    }
    
    @Sql("/SQL/data2.sql")
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        assertEquals(false, groupDao.isEmpty());
    }
}
