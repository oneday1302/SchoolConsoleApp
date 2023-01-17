package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;

public class RemoveStudentFromCourse implements Menu {
    private static final String NAME = "Remove the student from one of his courses";
    private final CourseDao coursesDao;
    private final StudentDao studentsDao;
    private final ConsoleInput input;

    public RemoveStudentFromCourse(CourseDao coursesDao, StudentDao studentsDao, ConsoleInput input) {
        if (coursesDao == null || studentsDao == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.coursesDao = coursesDao;
        this.studentsDao = studentsDao;
        this.input = input;
    }

    @Override
    public void execute() {
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
