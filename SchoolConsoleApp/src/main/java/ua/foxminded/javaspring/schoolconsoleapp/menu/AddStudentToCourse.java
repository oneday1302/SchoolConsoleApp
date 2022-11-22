package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.Student;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsCoursesDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsCoursesDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;

public class AddStudentToCourse implements Menu {
    private static final String NAME = "Add a student to the course";

    @Override
    public void execute() {
        CourseDao coursesDao = new CourseDaoImpl();
        StudentsDao studentsDao = new StudentsDaoImpl();
        StudentsCoursesDao studentsCoursesDao = new StudentsCoursesDaoImpl();
        ConsoleInput input = new ConsoleInput();

        coursesDao.getAllCourse().forEach(System.out::println);
        List<Student> students = studentsDao.getAllStudents();
        students.sort((o1, o2) -> o1.getStudentID() - o2.getStudentID());
        students.forEach(System.out::println);

        System.out.print("Enter the id of student: ");
        int studentId = input.getInt();
        System.out.print("Enter the id of course: ");
        int courseId = input.getInt();

        studentsCoursesDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
