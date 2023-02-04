package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentDao.add(student);
    }

    @Override
    public void addAll(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentDao.addAll(students);
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }

    @Override
    public void updateGroupIdRow(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentDao.updateGroupIdRow(student);
    }

    @Override
    public List<Student> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        return studentDao.findAllStudentsInTheCourse(courseName);
    }

    @Override
    public void delete(int id) {
        studentDao.delete(id);
    }

    @Override
    public void addStudentToCourse(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentDao.addStudentToCourse(student);
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        studentDao.removeStudentFromCourses(studentId);
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        studentDao.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        studentDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public boolean isEmpty() {
        return studentDao.isEmpty();
    }
}
