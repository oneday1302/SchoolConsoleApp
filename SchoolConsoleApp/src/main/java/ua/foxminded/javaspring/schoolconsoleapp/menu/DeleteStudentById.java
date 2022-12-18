package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class DeleteStudentById implements Menu {
    private static final String NAME = "Delete a student by the STUDENT_ID";
    private final StudentsDao studentsDao;
    private final ConsoleInput input;

    public DeleteStudentById(StudentsDao studentsDao, ConsoleInput input) {
        if (studentsDao == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.studentsDao = studentsDao;
        this.input = input;
    }

    @Override
    public void execute() {
        studentsDao.getAll().forEach(System.out::println);

        System.out.print("Enter the id of student: ");
        studentsDao.delete(input.getInt());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
