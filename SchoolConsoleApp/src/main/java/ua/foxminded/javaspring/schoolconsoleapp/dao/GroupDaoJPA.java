package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import java.util.StringJoiner;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

@Transactional
@Repository
@Profile("DataJPA")
public class GroupDaoJPA implements GroupDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        em.persist(group);
    }

    @Override
    public void addAll(List<Group> groups) {
        if (groups == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        for(Group group : groups) {
            em.persist(group);
        }
    }

    @Override
    public List<Group> getAll() {
        String sql = "SELECT g FROM Group g ORDER BY id";
        return em.createQuery(sql, Group.class).getResultList();
    }

    @Override
    public Group get(int id) {
        return em.find(Group.class, id);
    }

    @Override
    public List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT g FROM Group g")
           .add("JOIN Student s ON g.id = s.id")
           .add("GROUP BY g.id HAVING COUNT(s.id) <= ?1")
           .add("ORDER BY g.id");
        return em.createQuery(sql.toString(), Group.class).setParameter(1, (long)studentsNumber).getResultList();
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(group_id) FROM Group";
        Long count = em.createQuery(sql, Long.class).getSingleResult();
        return count == 0;
    }
}
