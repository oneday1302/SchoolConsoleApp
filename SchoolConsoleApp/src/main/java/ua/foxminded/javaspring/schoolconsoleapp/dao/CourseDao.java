package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;

public interface CourseDao<T> {

    void add(T course);
    
    void addAll(List<T> courses);

    List<T> getAll();
    
    boolean isEmpty();
}
