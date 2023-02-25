package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

public interface StudentDao {
    
    void add(Student student);
    
    void addAll(List<Student> students);

    List<Student> getAll();

    void updateGroupIdRow(Student student);

    List<Student> findAllStudentsInTheCourse(String courseName);

    void delete(int id);
    
    void addStudentToCourse(Student student);
    
    void removeStudentFromCourses(int studentId);
    
    void removeStudentFromCourse(int studentId, int courseId);
    
    void addStudentToCourse(int studentId, int courseId);
    
    boolean isEmpty();
}