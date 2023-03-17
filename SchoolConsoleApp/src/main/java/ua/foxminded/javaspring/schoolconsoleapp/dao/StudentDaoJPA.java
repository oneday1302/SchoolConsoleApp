package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Transactional
@Repository
@Profile("Hibernate")
public class StudentDaoJPA implements StudentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        em.persist(student);
    }

    @Override
    public void addAll(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        for (Student student : students) {
            em.persist(student);
        }
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT s FROM Student s ORDER BY id";
        return em.createQuery(sql, Student.class).getResultList();
    }

    @Override
    public void updateGroupIdRow(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        em.merge(student);
    }

    @Override
    public List<Student> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        String sql = "select s from Student s join s.courses c where c.name = ?1";
        return em.createQuery(sql, Student.class).setParameter(1, courseName).getResultList();
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Student WHERE id = ?1";
        em.createQuery(sql).setParameter(1, id).executeUpdate();
    }

    @Override
    public void addStudentToCourse(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        
        em.merge(student);
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        Student student = em.find(Student.class, studentId);
        student.removeStudentFromCourses();
        em.merge(student);
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        Student student = em.find(Student.class, studentId);
        student.removeStudentFromCourse(em.find(Course.class, courseId));
        em.merge(student);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        Student student = em.find(Student.class, studentId);
        student.addCourse(em.find(Course.class, courseId));
        em.merge(student);
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(id) FROM Student";
        Long count = em.createQuery(sql, Long.class).getSingleResult();
        return count == 0;
    }
}
