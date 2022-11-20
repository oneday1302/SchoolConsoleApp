package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;

public class AddNewStudent implements Menu {
    private static final String NAME = "Add new student";

    @Override
    public void execute() {
        StudentsDao studentsDao = new StudentsDaoImpl();
        ConsoleInput input = new ConsoleInput();

        System.out.print("Enter the first name: ");
        String fristName = input.getLine();
        System.out.print("Enter the last name: ");
        String lastName = input.getLine();

        Student student = new Student(fristName, lastName);
        studentsDao.addStudent(student);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
