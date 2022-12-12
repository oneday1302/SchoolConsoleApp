package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class RemoveStudentFromCourse implements Menu {
    private static final String NAME = "Remove the student from one of his courses";
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
        studentsDao.getAll().forEach(System.out::println);

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
