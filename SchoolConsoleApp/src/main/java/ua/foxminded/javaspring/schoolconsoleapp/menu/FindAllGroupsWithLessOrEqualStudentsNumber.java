package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.service.GroupService;

public class FindAllGroupsWithLessOrEqualStudentsNumber implements Menu {
    private static final String NAME = "Find all groups with less or equal students number";
    private final GroupService groupService;
    private final ConsoleInput input;

    public FindAllGroupsWithLessOrEqualStudentsNumber(GroupService groupService, ConsoleInput input) {
        if (groupService == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.groupService = groupService;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.print("Enter the students number: ");
        groupService.getAllGrupsWithLessOrEqualsStudentsNumber(input.getInt()).forEach(System.out::println);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
