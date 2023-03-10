package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import java.util.StringJoiner;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupEntity;

@Transactional
@Repository
@Profile("DataJPA")
public class GroupDaoJPA implements GroupDao<GroupEntity> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(GroupEntity group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        em.persist(group);
    }

    @Override
    public void addAll(List<GroupEntity> groups) {
        if (groups == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        for(GroupEntity group : groups) {
            em.persist(group);
        }
    }

    @Override
    public List<GroupEntity> getAll() {
        String sql = "SELECT g FROM GroupEntity g ORDER BY id";
        return em.createQuery(sql, GroupEntity.class).getResultList();
    }

    @Override
    public GroupEntity get(int id) {
        return em.find(GroupEntity.class, id);
    }

    @Override
    public List<GroupEntity> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT g FROM GroupEntity g")
           .add("JOIN StudentEntity s ON g.id = s.id")
           .add("GROUP BY g.id HAVING COUNT(s.id) <= :studentsNumber")
           .add("ORDER BY g.id");
        return em.createQuery(sql.toString(), GroupEntity.class).setParameter("studentsNumber", (long)studentsNumber).getResultList();
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(group_id) FROM GroupEntity";
        Long count = em.createQuery(sql, Long.class).getSingleResult();
        return count == 0;
    }
}
