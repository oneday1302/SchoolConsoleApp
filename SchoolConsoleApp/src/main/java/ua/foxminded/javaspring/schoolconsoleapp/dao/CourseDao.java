package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.Course;

public interface CourseDao {
    void addCourse(Course course);

    List<Course> getAllCourse();

}
