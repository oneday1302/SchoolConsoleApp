package ua.foxminded.javaspring.schoolconsoleapp.dao;

import ua.foxminded.javaspring.schoolconsoleapp.Student;

public interface StudentsCoursesDao {
    void addStudentsToCourses(Student student);
    
    void removeStudentFromCourse(int studentId);
    
    void removeStudentFromCourse(int studentId, int courseId);
    
    void addStudentToCourse(int studentId, int courseId);
}
