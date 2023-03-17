package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

@Repository
@Profile("DataJPA")
public class GroupDaoDataJPA implements GroupDao {

    private final GroupRepository repository;

    public GroupDaoDataJPA(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.save(group);
    }

    @Override
    public void addAll(List<Group> groups) {
        if (groups == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        repository.saveAll(groups);
    }

    @Override
    public List<Group> getAll() {
        return repository.findAll();
    }

    @Override
    public Group get(int id) {
        Group group = null;
        try {
            group = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
        return group;
    }

    @Override
    public List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        return repository.getAllGrupsWithLessOrEqualsStudentsNumber(studentsNumber);
    }

    @Override
    public boolean isEmpty() {
        return repository.countOfGroups() == 0;
    }
}
