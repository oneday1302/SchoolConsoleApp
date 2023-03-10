package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;

public interface CourseService<T> {

    void add(T course);

    void addAll(List<T> courses);

    List<T> getAll();

    boolean isEmpty();
}
