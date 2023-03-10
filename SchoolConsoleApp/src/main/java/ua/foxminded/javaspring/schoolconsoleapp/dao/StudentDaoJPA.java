package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

@Transactional
@Repository
@Profile("DataJPA")
public class StudentDaoJPA implements StudentDao<StudentEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        em.persist(student);
    }

    @Override
    public void addAll(List<StudentEntity> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        for (StudentEntity student : students) {
            em.persist(student);
        }
    }

    @Override
    public List<StudentEntity> getAll() {
        String sql = "SELECT s FROM StudentEntity s ORDER BY id";
        return em.createQuery(sql, StudentEntity.class).getResultList();
    }

    @Override
    public void updateGroupIdRow(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        em.merge(student);
    }

    @Override
    public List<StudentEntity> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        String sql = "select s from StudentEntity s join s.courses c where c.name = ?1";
        return em.createQuery(sql, StudentEntity.class).setParameter(1, courseName).getResultList();
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM StudentEntity WHERE id = ?1";
        em.createQuery(sql).setParameter(1, id).executeUpdate();
    }

    @Override
    public void addStudentToCourse(StudentEntity student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        
        em.merge(student);
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        StudentEntity student = em.find(StudentEntity.class, studentId);
        student.removeStudentFromCourses();
        em.merge(student);
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        StudentEntity student = em.find(StudentEntity.class, studentId);
        student.removeStudentFromCourse(em.find(CourseEntity.class, courseId));
        em.merge(student);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        StudentEntity student = em.find(StudentEntity.class, studentId);
        student.addCourse(em.find(CourseEntity.class, courseId));
        em.merge(student);
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(id) FROM StudentEntity";
        Long count = em.createQuery(sql, Long.class).getSingleResult();
        return count == 0;
    }
}
