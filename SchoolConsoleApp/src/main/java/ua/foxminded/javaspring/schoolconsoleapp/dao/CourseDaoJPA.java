package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

@Transactional
@Repository
@Profile("Hibernate")
public class CourseDaoJPA implements CourseDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        em.merge(course);
    }

    @Override
    public void addAll(List<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        for (Course course : courses) {
            em.merge(course);
        }
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT c FROM Course c ORDER BY id";
        return em.createQuery(sql, Course.class).getResultList();
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(course_id) FROM Course";
        Long count = em.createQuery(sql, Long.class).getSingleResult();
        return count == 0;
    }
}
