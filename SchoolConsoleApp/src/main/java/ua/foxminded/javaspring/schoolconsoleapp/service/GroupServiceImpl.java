package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;

@Service
public class GroupServiceImpl<T> implements GroupService<T> {

    private final GroupDao<T> groupDao;

    public GroupServiceImpl(GroupDao<T> groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public void add(T group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        groupDao.add(group);
    }

    @Override
    public void addAll(List<T> groups) {
        if (groups == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        groupDao.addAll(groups);
    }

    @Override
    public List<T> getAll() {
        return groupDao.getAll();
    }

    @Override
    public T get(int id) {
        return groupDao.get(id);
    }

    @Override
    public List<T> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        return groupDao.getAllGrupsWithLessOrEqualsStudentsNumber(studentsNumber);
    }

    @Override
    public boolean isEmpty() {
        return groupDao.isEmpty();
    }
}
