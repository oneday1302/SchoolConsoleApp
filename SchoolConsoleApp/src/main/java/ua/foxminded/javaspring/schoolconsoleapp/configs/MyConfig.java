package ua.foxminded.javaspring.schoolconsoleapp.configs;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddNewStudent;
import ua.foxminded.javaspring.schoolconsoleapp.menu.AddStudentToCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.DeleteStudentById;
import ua.foxminded.javaspring.schoolconsoleapp.menu.Exit;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllGroupsWithLessOrEqualStudentsNumber;
import ua.foxminded.javaspring.schoolconsoleapp.menu.FindAllStudentsInTheCourse;
import ua.foxminded.javaspring.schoolconsoleapp.menu.MenuGroup;
import ua.foxminded.javaspring.schoolconsoleapp.menu.RemoveStudentFromCourse;

@SpringBootConfiguration
public class MyConfig {
    
    private final ApplicationContext context;

    public MyConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    MenuGroup setConfigurationMenuGroup(MenuGroup menu) {
        menu.addMenu(context.getBean(FindAllGroupsWithLessOrEqualStudentsNumber.class));
        menu.addMenu(context.getBean(FindAllStudentsInTheCourse.class));
        menu.addMenu(context.getBean(AddNewStudent.class));
        menu.addMenu(context.getBean(DeleteStudentById.class));
        menu.addMenu(context.getBean(AddStudentToCourse.class));
        menu.addMenu(context.getBean(RemoveStudentFromCourse.class));
        menu.addMenu(context.getBean(Exit.class));
        return menu;
    }
    
    @Bean
    String getMenuGroupName() {
        return "";
    }
}
