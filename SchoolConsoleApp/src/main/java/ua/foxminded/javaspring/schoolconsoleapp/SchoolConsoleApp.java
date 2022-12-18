package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddNewStudent;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddStudentToCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.DeleteStudentById;
import ua.foxminded.javaspring.schoolconsoleapp.menu.Exit;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllGroupsWithLessOrEqualStudentsNumber;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllStudentsInTheCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.MenuGroup;
import ua.foxminded.javaspring.schoolconsoleapp.menu.RemoveStudentFromCourse;
import javax.sql.DataSource;

public class SchoolConsoleApp {
    
    public static void main(String[] args) {
        DataSource databaseConnect = new DataBaseUtility("config.properties");
        
        new SqlScriptRunner("schema.sql", databaseConnect).run();

        GroupDao groupsDao = new GroupDaoImpl(databaseConnect);
        CourseDao coursesDao = new CourseDaoImpl(databaseConnect);
        StudentsDao studentsDao = new StudentsDaoImpl(databaseConnect); 

        new GroupsGenerator(10).generate().forEach(groupsDao::add);
        new CoursesGenerator(new FileReader("coursesData.txt")).generate().forEach(coursesDao::add);
        new StudentsGenerator(new FileReader("firstNameData.txt"), new FileReader("lastNameData.txt"), 200).generate().forEach(studentsDao::add);

        List<Student> students = studentsDao.getAll();
        new GroupsDistributor(students, groupsDao.getAll()).distribute().forEach(studentsDao::updateGroupIdRow);
        new CoursesDistributor(students, coursesDao.getAll()).distribute().forEach(studentsDao::addStudentToCourse);

        ConsoleInput input = new ConsoleInput();
        
        MenuGroup menu = new MenuGroup(null, input);
        menu.addMenu(new FindAllGroupsWithLessOrEqualStudentsNumber(groupsDao, input));
        menu.addMenu(new FindAllStudentsInTheCourse(studentsDao, input));
        menu.addMenu(new AddNewStudent(studentsDao, input));
        menu.addMenu(new DeleteStudentById(studentsDao, input));
        menu.addMenu(new AddStudentToCourse(coursesDao, studentsDao, input));
        menu.addMenu(new RemoveStudentFromCourse(coursesDao, studentsDao, input));
        menu.addMenu(new Exit());
        menu.execute();
    }
}
