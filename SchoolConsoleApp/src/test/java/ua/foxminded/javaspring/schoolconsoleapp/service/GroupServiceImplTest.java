package ua.foxminded.javaspring.schoolconsoleapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.foxminded.javaspring.schoolconsoleapp.configs.ServiceLayerTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;
import ua.foxminded.javaspring.schoolconsoleapp.service.GroupService;

@SpringBootTest(classes = ServiceLayerTestConfig.class)
class GroupServiceImplTest {

    @Autowired
    GroupDao groupDao;

    @Autowired
    GroupService groupService;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupService.add(null);
        });
    }

    @Test
    void add__whenInputParamGroup() {
        Group group = new Group("AT-42");
        groupService.add(group);
        verify(groupDao, times(1)).add(group);
    }

    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            groupService.addAll(null);
        });
    }

    @Test
    void addAll__whenInputParamListOfGroup() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("AT-42"));
        groups.add(new Group("VK-13"));
        groupService.addAll(groups);
        verify(groupDao, times(1)).addAll(groups);
    }

    @Test
    void getAll_shouldReturnListOfGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("AT-42"));
        groups.add(new Group("VK-13"));
        when(groupDao.getAll()).thenReturn(groups);
        assertEquals(groups, groupService.getAll());
    }

    @Test
    void get_shouldReturnNull_whenInputIncorrectGroupId() {
        when(groupDao.get(1)).thenReturn(null);
        assertEquals(null, groupService.get(1));
    }

    @Test
    void get_shouldReturnGroup_whenInputGroupId() {
        Group group = new Group(1, "AT-42");
        when(groupDao.get(1)).thenReturn(group);
        assertEquals(group, groupService.get(1));
    }

    @Test
    void getAllGrupsWithLessOrEqualsStudentsNumber_shouldReturnListOfGroups_whenInputStudentsNumber() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("AT-42"));
        when(groupDao.getAllGrupsWithLessOrEqualsStudentsNumber(1)).thenReturn(groups);
        assertEquals(groups, groupService.getAllGrupsWithLessOrEqualsStudentsNumber(1));
    }

    @Test
    void isEmpty_shouldReturnTrue_whenTableIsEmpty() {
        when(groupDao.isEmpty()).thenReturn(true);
        assertEquals(true, groupService.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        when(groupDao.isEmpty()).thenReturn(false);
        assertEquals(false, groupService.isEmpty());
    }
}
