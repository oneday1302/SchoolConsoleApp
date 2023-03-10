package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;

public interface GroupService<T> {
    
    void add(T group);

    void addAll(List<T> groups);

    List<T> getAll();

    T get(int id);

    List<T> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber);

    boolean isEmpty();
}
