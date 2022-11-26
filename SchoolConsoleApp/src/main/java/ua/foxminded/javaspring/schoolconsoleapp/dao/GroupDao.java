package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.Group;

public interface GroupDao {
    
    void add(Group group);

    List<Group> getAll();

    Group get(int id);
}
