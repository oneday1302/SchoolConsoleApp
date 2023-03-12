package ua.foxminded.javaspring.schoolconsoleapp.menu;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

@Component
public class DeleteStudentById implements Menu {
    
    private static final String NAME = "Delete a student by the STUDENT_ID";
    private final StudentService studentService;
    private final ConsoleInput input;

    public DeleteStudentById(StudentService studentService, ConsoleInput input) {
        if (studentService == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.studentService = studentService;
        this.input = input;
    }

    @Override
    public void execute() {
        studentService.getAll().forEach(System.out::println);

        System.out.print("Enter the id of student: ");
        studentService.delete(input.getInt());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
