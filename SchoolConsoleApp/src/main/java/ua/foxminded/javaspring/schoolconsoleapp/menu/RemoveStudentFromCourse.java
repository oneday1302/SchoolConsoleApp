package ua.foxminded.javaspring.schoolconsoleapp.menu;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.service.CourseService;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

@Component
public class RemoveStudentFromCourse implements Menu {
    
    private static final String NAME = "Remove the student from one of his courses";
    private final CourseService courseService;
    private final StudentService studentService;
    private final ConsoleInput input;

    public RemoveStudentFromCourse(CourseService courseService, StudentService studentService, ConsoleInput input) {
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

        studentService.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
