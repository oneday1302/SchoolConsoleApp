package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class AddNewStudent implements Menu {
    private static final String NAME = "Add new student";
    private final StudentsDao studentsDao;

    public AddNewStudent(StudentsDao studentsDao) {
        if (studentsDao == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.studentsDao = studentsDao;
    }

    @Override
    public void execute() {
        ConsoleInput input = new ConsoleInput();

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
