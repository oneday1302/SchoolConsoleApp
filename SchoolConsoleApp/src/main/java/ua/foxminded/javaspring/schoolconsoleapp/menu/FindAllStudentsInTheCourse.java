package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;

public class FindAllStudentsInTheCourse implements Menu {
    private static final String NAME = "Find all students related to the course with the given name";
    private final StudentDao studentsDao;
    private final ConsoleInput input;

    public FindAllStudentsInTheCourse(StudentDao studentsDao, ConsoleInput input) {
        if (studentsDao == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.studentsDao = studentsDao;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.print("Enter the name of course: ");
        input.getLine();
        studentsDao.findAllStudentsInTheCourse(input.getLine()).forEach(System.out::println);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
