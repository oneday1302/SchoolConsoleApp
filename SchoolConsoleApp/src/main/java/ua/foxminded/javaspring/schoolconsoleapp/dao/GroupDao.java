package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.Group;

public interface GroupDao {
    void addGroup(Group group);
    List<Group> getAllGroup();
    Group getGroupById(int id);
}
