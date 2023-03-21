package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@ActiveProfiles("DataJPA")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = GroupDaoDataJPA.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/SQL/afterEach2.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GroupDaoDataJPATest {
    
    @Autowired
    private TestEntityManager em;
    
    @Autowired
    private GroupDao groupDao;

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
        assertEquals(group, em.find(Group.class, 1));
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
        groups.add(new Group("AT-42"));
        groups.add(new Group("VK-13"));
        groups.add(new Group("GG-01"));
        groupDao.addAll(groups);

        String sql = "SELECT g FROM Group g";
        assertEquals(groups, em.getEntityManager().createQuery(sql, Group.class).getResultList());
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
