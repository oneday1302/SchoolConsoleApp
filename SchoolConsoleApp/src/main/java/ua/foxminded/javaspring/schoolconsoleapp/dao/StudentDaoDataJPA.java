package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Repository
@Profile("DataJPA")
public class StudentDaoDataJPA implements StudentDao {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentDaoDataJPA(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentRepository.save(student);
    }

    @Override
    public void addAll(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentRepository.saveAll(students);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public void updateGroupIdRow(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        studentRepository.save(student);
    }

    @Override
    public List<Student> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        return studentRepository.findAllStudentsInTheCourseByCoursesName(courseName);
    }

    @Override
    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void addStudentToCourse(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        
        studentRepository.save(student);
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new IllegalArgumentException("Entity no found.");
        }
        student.get().removeStudentFromCourses();
        studentRepository.save(student.get());
        
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new IllegalArgumentException("Entity no found.");
        }
        student.get().removeStudentFromCourse(courseId);
        studentRepository.save(student.get());
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new IllegalArgumentException("Entity no found.");
        }
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new IllegalArgumentException("Entity no found.");
        }
        student.get().addCourse(course.get());
        studentRepository.save(student.get());
    }

    @Override
    public boolean isEmpty() {
        return studentRepository.count() == 0;
    }
}
