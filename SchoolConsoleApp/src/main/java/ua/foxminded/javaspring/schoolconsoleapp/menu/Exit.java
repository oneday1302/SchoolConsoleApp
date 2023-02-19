package ua.foxminded.javaspring.schoolconsoleapp.menu;

import org.springframework.stereotype.Component;

@Component
public class Exit implements Menu {
    
    private static final String NAME = "Exit";

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
