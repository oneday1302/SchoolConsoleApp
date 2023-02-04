package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddNewStudent;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddStudentToCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.DeleteStudentById;
import ua.foxminded.javaspring.schoolconsoleapp.menu.Exit;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllGroupsWithLessOrEqualStudentsNumber;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllStudentsInTheCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.MenuGroup;
import ua.foxminded.javaspring.schoolconsoleapp.menu.RemoveStudentFromCourse;
import ua.foxminded.javaspring.schoolconsoleapp.service.CourseService;
import ua.foxminded.javaspring.schoolconsoleapp.service.GroupService;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ConsoleInput input;

    @Override
    public void run(String... args) throws Exception {
        if (groupService.isEmpty() && courseService.isEmpty() && studentService.isEmpty()) {
            groupService.addAll(new GroupsGenerator(10).generate());
            courseService.addAll(new CoursesGenerator(new FileReader("coursesData.txt")).generate());
            studentService.addAll(new StudentsGenerator(new FileReader("firstNameData.txt"), new FileReader("lastNameData.txt"), 200).generate());

            List<Student> students = studentService.getAll();
            new GroupsDistributor(students, groupService.getAll()).distribute().forEach(studentService::updateGroupIdRow);
            new CoursesDistributor(students, courseService.getAll()).distribute().forEach(studentService::addStudentToCourse);
        }

        MenuGroup menu = new MenuGroup(null, input);
        menu.addMenu(new FindAllGroupsWithLessOrEqualStudentsNumber(groupService, input));
        menu.addMenu(new FindAllStudentsInTheCourse(studentService, input));
        menu.addMenu(new AddNewStudent(studentService, input));
        menu.addMenu(new DeleteStudentById(studentService, input));
        menu.addMenu(new AddStudentToCourse(courseService, studentService, input));
        menu.addMenu(new RemoveStudentFromCourse(courseService, studentService, input));
        menu.addMenu(new Exit());
        menu.execute();
    }
}
