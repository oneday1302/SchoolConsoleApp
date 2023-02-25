package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

public interface CourseDao {

    void add(Course course);
    
    void addAll(List<Course> courses);

    List<Course> getAll();
    
    boolean isEmpty();
}
