package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddNewStudent;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddStudentToCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.DeleteStudentById;
import ua.foxminded.javaspring.schoolconsoleapp.menu.Exit;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllGroupsWithLessOrEqualStudentsNumber;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllStudentsInTheCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.MenuGroup;
import ua.foxminded.javaspring.schoolconsoleapp.menu.RemoveStudentFromCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolConsoleApp implements CommandLineRunner {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private StudentDao studentDao;

    public static void main(String[] args) {
        SpringApplication.run(SchoolConsoleApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (groupDao.isEmpty() && courseDao.isEmpty() && studentDao.isEmpty()) {
            new GroupsGenerator(10).generate().forEach(groupDao::add);
            new CoursesGenerator(new FileReader("coursesData.txt")).generate().forEach(courseDao::add);
            new StudentsGenerator(new FileReader("firstNameData.txt"), new FileReader("lastNameData.txt"), 200).generate().forEach(studentDao::add);

            List<Student> students = studentDao.getAll();
            new GroupsDistributor(students, groupDao.getAll()).distribute().forEach(studentDao::updateGroupIdRow);
            new CoursesDistributor(students, courseDao.getAll()).distribute().forEach(studentDao::addStudentToCourse);
        }
        
        ConsoleInput input = new ConsoleInput();

        MenuGroup menu = new MenuGroup(null, input);
        menu.addMenu(new FindAllGroupsWithLessOrEqualStudentsNumber(groupDao, input));
        menu.addMenu(new FindAllStudentsInTheCourse(studentDao, input));
        menu.addMenu(new AddNewStudent(studentDao, input));
        menu.addMenu(new DeleteStudentById(studentDao, input));
        menu.addMenu(new AddStudentToCourse(courseDao, studentDao, input));
        menu.addMenu(new RemoveStudentFromCourse(courseDao, studentDao, input));
        menu.addMenu(new Exit());
        menu.execute();
    }
}
