package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.annotation.DaoTest;
import ua.foxminded.javaspring.schoolconsoleapp.configs.DaoTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;
import ua.foxminded.javaspring.schoolconsoleapp.mapper.GroupMapper;

@ActiveProfiles({ "nativeJDBC", "JDBCTemplate", "Hibernate", "DataJPA" })
@SpringBootTest(classes = DaoTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupDaoTest {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    List<GroupDao> impls;

    Stream<GroupDao> impls() {
        return impls.stream();
    }

    @DaoTest
    void add_shouldReturnIllegalArgumentException_whenInputParamNull(GroupDao groupDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            groupDao.add(null);
        });
    }

    @Sql("/SQL/afterEach2.sql")
    @Sql("/SQL/setGroupSetval.sql")
    @DaoTest
    void add__whenInputParamGroup(GroupDao groupDao) {
        Group group = new Group(1, "AT-42");
        groupDao.add(group);

        String sql = "SELECT * FROM school.groups";
        assertEquals(group, jdbc.query(sql, new GroupMapper()).get(0));
    }
    
    @DaoTest
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull(GroupDao groupDao) {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            groupDao.addAll(null);
        });
    }

    @Sql("/SQL/afterEach2.sql")
    @Sql("/SQL/setGroupSetval.sql")
    @DaoTest
    void addAll__whenInputParamListOfGroup(GroupDao groupDao) {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AT-42"));
        groups.add(new Group(2, "VK-13"));
        groups.add(new Group(3, "GG-01"));
        groupDao.addAll(groups);

        String sql = "SELECT * FROM school.groups";
        assertEquals(groups, jdbc.query(sql, new GroupMapper()));
    }

    @Sql("/SQL/afterEach2.sql")
    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/data2.sql")
    @DaoTest
    void getAll_shouldReturnListOfGroups(GroupDao groupDao) {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AT-42"));
        groups.add(new Group(2, "VK-13"));
        groups.add(new Group(3, "GG-01"));

        assertEquals(groups, groupDao.getAll());
    }

    @Sql("/SQL/afterEach2.sql")
    @DaoTest
    void get_shouldReturnNull_whenInputIncorrectGroupId(GroupDao groupDao) {
        assertEquals(null, groupDao.get(1));
    }

    @Sql("/SQL/afterEach2.sql")
    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/data3.sql")
    @DaoTest
    void get_shouldReturnGroup_whenInputGroupId(GroupDao groupDao) {
        Group group = new Group(1, "AT-42");
        assertEquals(group, groupDao.get(1));
    }

    @Sql("/SQL/afterEach2.sql")
    @Sql("/SQL/setStudentSetval.sql")
    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/data4.sql")
    @DaoTest
    void getAllGrupsWithLessOrEqualsStudentsNumber_shouldReturnListOfGroups_whenInputStudentsNumber(GroupDao groupDao) {
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
    
    @Sql("/SQL/afterEach2.sql")
    @DaoTest
    void isEmpty_shouldReturnTrue_whenTableIsEmpty(GroupDao groupDao) {
        assertEquals(true, groupDao.isEmpty());
    }
    
    @Sql("/SQL/afterEach2.sql")
    @Sql("/SQL/data2.sql")
    @DaoTest
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty(GroupDao groupDao) {
        assertEquals(false, groupDao.isEmpty());
    }
}
