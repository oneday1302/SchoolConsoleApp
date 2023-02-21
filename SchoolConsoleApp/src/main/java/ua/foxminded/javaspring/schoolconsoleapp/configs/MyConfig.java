package ua.foxminded.javaspring.schoolconsoleapp.configs;

import java.util.List;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;
import ua.foxminded.javaspring.schoolconsoleapp.menu.Menu;
import ua.foxminded.javaspring.schoolconsoleapp.menu.MenuGroup;

@SpringBootConfiguration
public class MyConfig {
    
    @Bean
    public MenuGroup mainMenu(List<Menu> items, ConsoleInput input) {
      return new MenuGroup(null, input, items);
    }
}
