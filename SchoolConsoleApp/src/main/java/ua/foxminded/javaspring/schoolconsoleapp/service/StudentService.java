package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;

public interface StudentService<T> {

    void add(T student);

    void addAll(List<T> students);

    List<T> getAll();

    void updateGroupIdRow(T student);

    List<T> findAllStudentsInTheCourse(String courseName);

    void delete(int id);

    void addStudentToCourse(T student);

    void removeStudentFromCourses(int studentId);

    void removeStudentFromCourse(int studentId, int courseId);

    void addStudentToCourse(int studentId, int courseId);

    boolean isEmpty();
}
