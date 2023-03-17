package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Repository
@Profile("DataJPA")
public class StudentDaoDataJPA implements StudentDao {

    private final StudentRepository repository;
    
    public StudentDaoDataJPA(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        repository.save(student);
    }

    @Override
    public void addAll(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        repository.saveAll(students);
    }

    @Override
    public List<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public void updateGroupIdRow(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        repository.save(student);
    }

    @Override
    public List<Student> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        return repository.findAllStudentsInTheCourse(courseName);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public void addStudentToCourse(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        
        repository.save(student);
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        Student student = repository.findById(studentId).get();
        student.removeStudentFromCourses();
        repository.save(student);
        
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        Student student = repository.findById(studentId).get();
        student.removeStudentFromCourse(courseId);
        repository.save(student);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        Student student = repository.findById(studentId).get();
        student.addCourse(repository.getCourse(courseId));
        repository.save(student);
    }

    @Override
    public boolean isEmpty() {
        return repository.countOfStudents() == 0;
    }
}
