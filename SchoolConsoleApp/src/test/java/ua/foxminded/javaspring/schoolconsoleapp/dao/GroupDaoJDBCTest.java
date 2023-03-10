package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupMapper;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@SpringBootTest(classes = DaoTestConfig.class)
@ActiveProfiles("JDBCTemplate")
class GroupDaoJDBCTest {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    GroupDao<Group> groupDao;

    @AfterEach
    void cleanup() {
        jdbc.update("TRUNCATE TABLE school.groups CASCADE; TRUNCATE TABLE school.students CASCADE;");
    }

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupDao.add(null);
        });
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Test
    void add__whenInputParamGroup() {
        Group group = new Group(1, "AT-42");
        groupDao.add(group);

        String sql = "SELECT * FROM school.groups";
        assertEquals(group, jdbc.query(sql, new GroupMapper()).get(0));
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupDao.addAll(null);
        });
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Test
    void addAll__whenInputParamListOfGroup() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AT-42"));
        groups.add(new Group(2, "VK-13"));
        groups.add(new Group(3, "GG-01"));
        groupDao.addAll(groups);

        String sql = "SELECT * FROM school.groups";
        assertEquals(groups, jdbc.query(sql, new GroupMapper()));
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
