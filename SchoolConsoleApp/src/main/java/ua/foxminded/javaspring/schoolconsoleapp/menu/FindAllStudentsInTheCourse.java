package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class FindAllStudentsInTheCourse implements Menu {
    private static final String NAME = "Find all students related to the course with the given name";
    private final StudentsDao studentsDao;

    public FindAllStudentsInTheCourse(StudentsDao studentsDao) {
        if (studentsDao == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.studentsDao = studentsDao;
    }

    @Override
    public void execute() {
        ConsoleInput input = new ConsoleInput();

        System.out.print("Enter the name of course: ");
        studentsDao.findAllStudentsInTheCourse(input.getLine()).forEach(System.out::println);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
