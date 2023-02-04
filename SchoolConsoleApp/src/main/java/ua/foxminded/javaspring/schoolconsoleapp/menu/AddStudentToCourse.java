package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.service.CourseService;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

public class AddStudentToCourse implements Menu {
    private static final String NAME = "Add a student to the course";
    private final CourseService courseService;
    private final StudentService studentService;
    private final ConsoleInput input;

    public AddStudentToCourse(CourseService courseService, StudentService studentService, ConsoleInput input) {
        if (courseService == null || studentService == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.courseService = courseService;
        this.studentService = studentService;
        this.input = input;
    }

    @Override
    public void execute() {
        courseService.getAll().forEach(System.out::println);
        studentService.getAll().forEach(System.out::println);

        System.out.print("Enter the id of student: ");
        int studentId = input.getInt();
        System.out.print("Enter the id of course: ");
        int courseId = input.getInt();

        studentService.addStudentToCourse(studentId, courseId);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
