package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.Course;

public interface CourseService {

    void add(Course course);

    void addAll(List<Course> courses);

    List<Course> getAll();

    boolean isEmpty();
}
