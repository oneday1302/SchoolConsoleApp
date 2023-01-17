package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;

public class AddNewStudent implements Menu {
    private static final String NAME = "Add new student";
    private final StudentDao studentsDao;
    private final ConsoleInput input;

    public AddNewStudent(StudentDao studentsDao, ConsoleInput input) {
        if (studentsDao == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.studentsDao = studentsDao;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.print("Enter the first name: ");
        String fristName = input.getLine();
        System.out.print("Enter the last name: ");
        String lastName = input.getLine();

        studentsDao.add(new Student(fristName, lastName));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
