package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;

public class AddStudentToCourse implements Menu {
    private static final String NAME = "Add a student to the course";
    private final CourseDao coursesDao;
    private final StudentsDao studentsDao;

    public AddStudentToCourse(CourseDao coursesDao, StudentsDao studentsDao) {
        if (coursesDao == null || studentsDao == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
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

        studentsDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
