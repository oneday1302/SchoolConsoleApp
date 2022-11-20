package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;

public class FindAllStudentsInTheCourse implements Menu {
    private static final String NAME = "Find all groups with less or equal students’ number";

    @Override
    public void execute() {
        StudentsDao studentsDao = new StudentsDaoImpl();
        ConsoleInput input = new ConsoleInput();

        System.out.print("Enter the name of course: ");
        studentsDao.findAllStudentsInTheCourse(input.getLine()).forEach(System.out::println);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
