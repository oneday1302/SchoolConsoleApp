package ua.foxminded.javaspring.schoolconsoleapp.menu;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

public class FindAllStudentsInTheCourse implements Menu {
    private static final String NAME = "Find all students related to the course with the given name";
    private final StudentService studentService;
    private final ConsoleInput input;

    public FindAllStudentsInTheCourse(StudentService studentService, ConsoleInput input) {
        if (studentService == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.studentService = studentService;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.print("Enter the name of course: ");
        input.getLine();
        studentService.findAllStudentsInTheCourse(input.getLine()).forEach(System.out::println);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
