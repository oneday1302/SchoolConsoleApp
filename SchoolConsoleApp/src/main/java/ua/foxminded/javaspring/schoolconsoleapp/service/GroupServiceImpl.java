package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;

    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        groupDao.add(group);
    }

    @Override
    public void addAll(List<Group> groups) {
        if (groups == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        groupDao.addAll(groups);
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public Group get(int id) {
        return groupDao.get(id);
    }

    @Override
    public List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(int studentsNumber) {
        return groupDao.getAllGrupsWithLessOrEqualsStudentsNumber(studentsNumber);
    }

    @Override
    public boolean isEmpty() {
        return groupDao.isEmpty();
    }
}
