package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.javaspring.schoolconsoleapp.distributor.CoursesDistributor;
import ua.foxminded.javaspring.schoolconsoleapp.distributor.GroupsDistributor;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;
import ua.foxminded.javaspring.schoolconsoleapp.generator.CoursesGenerator;
import ua.foxminded.javaspring.schoolconsoleapp.generator.GroupsGenerator;
import ua.foxminded.javaspring.schoolconsoleapp.generator.StudentsGenerator;
import ua.foxminded.javaspring.schoolconsoleapp.menu.Menu;
import ua.foxminded.javaspring.schoolconsoleapp.service.CourseService;
import ua.foxminded.javaspring.schoolconsoleapp.service.GroupService;
import ua.foxminded.javaspring.schoolconsoleapp.service.StudentService;

@Component
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {

    private final CourseService courseService;
    private final GroupService groupService;
    private final StudentService studentService;
    private final Menu menu;
    
    public ApplicationStartupRunner(CourseService courseService, GroupService groupService, StudentService studentService, @Qualifier("mainMenu") Menu menu) {
        this.courseService = courseService;
        this.groupService = groupService;
        this.studentService = studentService;
        this.menu = menu;
    }

    @Override
    public void run(String... args) throws Exception {
        if (groupService.isEmpty() && courseService.isEmpty() && studentService.isEmpty()) {
            log.info("Filling the database with data is statring...");
            groupService.addAll(new GroupsGenerator(10).generate());
            courseService.addAll(new CoursesGenerator(new FileReader("coursesData.txt")).generate());
            studentService.addAll(new StudentsGenerator(new FileReader("firstNameData.txt"), new FileReader("lastNameData.txt"), 200).generate());

            List<Student> students = studentService.getAll();
            new GroupsDistributor(students, groupService.getAll()).distribute().forEach(studentService::updateGroupIdRow);
            new CoursesDistributor(students, courseService.getAll()).distribute().forEach(studentService::addStudentToCourse);
            log.info("Filling the database with data is finished.");
        }
       menu.execute();
    }
}
