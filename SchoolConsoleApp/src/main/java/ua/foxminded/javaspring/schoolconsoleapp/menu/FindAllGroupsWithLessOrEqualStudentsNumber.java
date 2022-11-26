package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.ArrayList;
import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Group;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class FindAllGroupsWithLessOrEqualStudentsNumber implements Menu {
    private static final String NAME = "Find all groups with less or equal students number";
    private final GroupDao groupsDao;
    private final StudentsDao studentsDao;

    public FindAllGroupsWithLessOrEqualStudentsNumber(GroupDao groupsDao, StudentsDao studentsDao) {
        if (groupsDao == null || studentsDao == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.groupsDao = groupsDao;
        this.studentsDao = studentsDao;
    }

    @Override
    public void execute() {
        ConsoleInput input = new ConsoleInput();

        System.out.print("Enter the students number: ");
        int studentsNumber = input.getInt();

        List<Group> groups = new ArrayList<>();
        for (Student student : studentsDao.getAll()) {
            if (student.getGroup() != null) {
                for (Group group : groupsDao.getAll()) {
                    if (group.getName().equals(student.getGroup().getName())) {
                        if (groups.contains(group)) {
                            groups.get(groups.indexOf(group)).addStudent(student);
                        } else {
                            group.addStudent(student);
                            groups.add(group);
                        }
                        break;
                    }
                }
            }
        }

        for (Group group : groups) {
            if (group.getStudents().size() <= studentsNumber) {
                System.out.println(group);
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
