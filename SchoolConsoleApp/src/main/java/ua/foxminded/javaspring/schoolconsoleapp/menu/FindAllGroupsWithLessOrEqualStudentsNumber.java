package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Group;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;

public class FindAllGroupsWithLessOrEqualStudentsNumber implements Menu {
    private static final String NAME = " Find all groups with less or equal students number";

    @Override
    public void execute() {
        GroupDao groupsDao = new GroupDaoImpl();
        StudentsDao studentsDao = new StudentsDaoImpl();
        ConsoleInput input = new ConsoleInput();

        System.out.print("Enter the students number: ");
        int studentsNumber = input.getInt();

        List<Group> groups = groupsDao.getAllGroup();

        for (Student student : studentsDao.getAllStudents()) {
            if (student.getGroup() != null) {
                for (Group group : groups) {
                    if (group.getGroupName() == student.getGroup().getGroupName()) {
                        group.addStudent(student);
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
