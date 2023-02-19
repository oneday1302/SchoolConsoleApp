package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

public interface GroupService {
    
    void add(Group group);

    void addAll(List<Group> groups);

    List<Group> getAll();

    Group get(int id);

    List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber);

    boolean isEmpty();
}
