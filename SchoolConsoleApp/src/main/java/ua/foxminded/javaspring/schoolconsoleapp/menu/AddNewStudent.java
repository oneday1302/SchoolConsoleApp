package ua.foxminded.javaspring.schoolconsoleapp.menu;

import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

@Component
public class AddNewStudent implements Menu {
    
    private static final String NAME = "Add new student";
    private final StudentService studentService;
    private final ConsoleInput input;

    public AddNewStudent(StudentService studentService, ConsoleInput input) {
        if (studentService == null || input == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.studentService = studentService;
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.print("Enter the first name: ");
        String fristName = input.getLine();
        System.out.print("Enter the last name: ");
        String lastName = input.getLine();
        
        studentService.add(new Student(fristName, lastName));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
