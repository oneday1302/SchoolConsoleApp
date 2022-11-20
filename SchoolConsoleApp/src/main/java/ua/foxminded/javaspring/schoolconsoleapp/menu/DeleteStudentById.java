package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;

public class DeleteStudentById implements Menu {
    private static final String NAME = "Delete a student by the STUDENT_ID";

    @Override
    public void execute() {
        StudentsDao studentsDao = new StudentsDaoImpl();
        ConsoleInput input = new ConsoleInput();

        List<Student> students = studentsDao.getAllStudents();
        students.sort((o1, o2) -> o1.getStudentID() - o2.getStudentID());
        students.forEach(System.out::println);

        System.out.print("Enter the id of student: ");
        studentsDao.deleteStudentById(input.getInt());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
