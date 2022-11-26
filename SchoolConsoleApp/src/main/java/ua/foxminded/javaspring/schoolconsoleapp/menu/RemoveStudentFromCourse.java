package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class RemoveStudentFromCourse implements Menu {
    private static final String NAME = "Remove the student from one of their courses";
    private final CourseDao coursesDao;
    private final StudentsDao studentsDao;

    public RemoveStudentFromCourse(CourseDao coursesDao, StudentsDao studentsDao) {
        this.coursesDao = coursesDao;
        this.studentsDao = studentsDao;
    }

    @Override
    public void execute() {
        ConsoleInput input = new ConsoleInput();

        coursesDao.getAll().forEach(System.out::println);
        List<Student> students = studentsDao.getAll();
        students.sort((o1, o2) -> o1.getId() - o2.getId());
        students.forEach(System.out::println);

        System.out.print("Enter the id of student: ");
        int studentId = input.getInt();
        System.out.print("Enter the id of course: ");
        int courseId = input.getInt();

        studentsDao.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
