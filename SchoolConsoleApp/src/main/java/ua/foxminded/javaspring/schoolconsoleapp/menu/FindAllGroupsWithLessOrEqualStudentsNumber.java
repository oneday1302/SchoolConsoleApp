package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;

public class FindAllGroupsWithLessOrEqualStudentsNumber implements Menu {
    private static final String NAME = "Find all groups with less or equal students number";
    private final GroupDao groupsDao;

    public FindAllGroupsWithLessOrEqualStudentsNumber(GroupDao groupsDao) {
        if (groupsDao == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.groupsDao = groupsDao;
    }

    @Override
    public void execute() {
        ConsoleInput input = new ConsoleInput();

        System.out.print("Enter the students number: ");
        groupsDao.getAllGrupsWithLessOrEqualsStudentsNumber(input.getInt()).forEach(System.out::println);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
