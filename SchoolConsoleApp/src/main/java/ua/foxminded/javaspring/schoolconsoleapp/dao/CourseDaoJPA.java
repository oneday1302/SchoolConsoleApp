package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;

@Transactional
@Repository
@Profile("DataJPA")
public class CourseDaoJPA implements CourseDao<CourseEntity> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(CourseEntity course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        em.persist(course);
    }

    @Override
    public void addAll(List<CourseEntity> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        for (CourseEntity course : courses) {
            em.persist(course);
        }
    }

    @Override
    public List<CourseEntity> getAll() {
        String sql = "SELECT c FROM CourseEntity c ORDER BY id";
        return em.createQuery(sql, CourseEntity.class).getResultList();
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(course_id) FROM CourseEntity";
        Long count = em.createQuery(sql, Long.class).getSingleResult();
        return count == 0;
    }
}
