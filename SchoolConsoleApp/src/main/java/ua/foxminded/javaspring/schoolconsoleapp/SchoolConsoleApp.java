package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;

import ua.foxminded.javaspring.schoolconsoleapp.dao.CoursesDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CoursesDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.GroupDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsCoursesDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsCoursesDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDao;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentsDaoImpl;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddNewStudent;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddStudentToCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.DeleteStudentById;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllGroupsWithLessOrEqualStudentsNumber;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllStudentsInTheCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.MenuGroups;
import ua.foxminded.javaspring.schoolconsoleapp.menu.RemoveStudentFromCourse;

public class SchoolConsoleApp {
    public static void main(String[] args) {
        new SqlScriptRunner("schema.sql").run();

        GroupDao groupsDao = new GroupDaoImpl();
        CoursesDao coursesDao = new CoursesDaoImpl();
        StudentsDao studentsDao = new StudentsDaoImpl();
        StudentsCoursesDao studentsCoursesDao = new StudentsCoursesDaoImpl();

        new GroupsGenerator(10).generate().forEach(groupsDao::addGroup);
        new CoursesGenerator().generate().forEach(coursesDao::addCourse);
        new StudentsGenerator(200).generate().forEach(studentsDao::addStudent);

        List<Student> students = studentsDao.getAllStudents();

        GroupsDistributor groupsDistributor = new GroupsDistributor(students, groupsDao.getAllGroup());
        groupsDistributor.distribute();
        groupsDistributor.getStudents().forEach(studentsDao::updateGroupIdRow);

        CoursesDistributor coursesDistributor = new CoursesDistributor(students, coursesDao.getAllCourse());
        coursesDistributor.distribute();
        coursesDistributor.getStudents().forEach(studentsCoursesDao::addStudentsToCourses);
        
        MenuGroups menu = new MenuGroups();
        menu.addMenu(new FindAllGroupsWithLessOrEqualStudentsNumber());
        menu.addMenu(new FindAllStudentsInTheCourse());
        menu.addMenu(new AddNewStudent());
        menu.addMenu(new DeleteStudentById());
        menu.addMenu(new AddStudentToCourse());
        menu.addMenu(new RemoveStudentFromCourse());
        menu.execute();
    }
}
