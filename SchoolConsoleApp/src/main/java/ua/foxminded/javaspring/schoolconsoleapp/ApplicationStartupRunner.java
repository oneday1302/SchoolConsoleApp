package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
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

@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public void run(String... args) throws Exception {
        if (groupDao.isEmpty() && courseDao.isEmpty() && studentDao.isEmpty()) {
            groupDao.addAll(new GroupsGenerator(10).generate());
            courseDao.addAll(new CoursesGenerator(new FileReader("coursesData.txt")).generate());
            studentDao.addAll(new StudentsGenerator(new FileReader("firstNameData.txt"), new FileReader("lastNameData.txt"), 200).generate());

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
