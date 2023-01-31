package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.Group;

public interface GroupDao {
    
    void add(Group group);
    
    void addAll(List<Group> groups);

    List<Group> getAll();

    Group get(int id);
    
    List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber);
    
    boolean isEmpty();
}
