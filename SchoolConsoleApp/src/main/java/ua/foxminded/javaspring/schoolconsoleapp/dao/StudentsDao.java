package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.Student;

public interface StudentsDao {
    void addStudent(Student student);

    List<Student> getAllStudents();

    void updateGroupIdRow(Student student);

    List<Student> findAllStudentsInTheCourse(String courseName);

    void deleteStudentById(int id);
}