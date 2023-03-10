package ua.foxminded.javaspring.schoolconsoleapp.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

@ActiveProfiles("DataJPA")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = GroupDaoJPA.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/SQL/afterEach2.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GroupDaoJPATest {

    @Autowired
    private EntityManager em;

    @Autowired
    GroupDao<GroupEntity> groupDao;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupDao.add(null);
        });
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Test
    void add__whenInputParamGroup() {
        GroupEntity group = new GroupEntity("AT-42");
        groupDao.add(group);

        String sql = "SELECT g FROM GroupEntity g";
        assertEquals(group, em.createQuery(sql, GroupEntity.class).getSingleResult());
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
        List<GroupEntity> groups = new ArrayList<>();
        groups.add(new GroupEntity("AT-42"));
        groups.add(new GroupEntity("VK-13"));
        groups.add(new GroupEntity("GG-01"));
        groupDao.addAll(groups);

        String sql = "SELECT g FROM GroupEntity g";
        assertEquals(groups, em.createQuery(sql, GroupEntity.class).getResultList());
    }

    @Sql("/SQL/setGroupSetval.sql")
    @Sql("/SQL/data2.sql")
    @Test
    void getAll_shouldReturnListOfGroups() {
        List<GroupEntity> groups = new ArrayList<>();
        groups.add(new GroupEntity(1, "AT-42"));
        groups.add(new GroupEntity(2, "VK-13"));
        groups.add(new GroupEntity(3, "GG-01"));

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
        GroupEntity group = new GroupEntity(1, "AT-42");
        assertEquals(group, groupDao.get(1));
    }

    @Sql("/SQL/data4.sql")
    @Test
    void getAllGrupsWithLessOrEqualsStudentsNumber_shouldReturnListOfGroups_whenInputStudentsNumber() {
        GroupEntity group = new GroupEntity(1, "VK-13");

        StudentEntity student1 = new StudentEntity("Jacob", "Smith");
        student1.setGroup(group);
        StudentEntity student2 = new StudentEntity("Emily", "Jones");
        student2.setGroup(group);
        StudentEntity student3 = new StudentEntity("Michael", "Taylor");
        student3.setGroup(group);
        List<StudentEntity> students = new ArrayList<>();
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
