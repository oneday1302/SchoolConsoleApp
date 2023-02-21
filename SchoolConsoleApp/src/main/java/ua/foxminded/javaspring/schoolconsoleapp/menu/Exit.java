package ua.foxminded.javaspring.schoolconsoleapp.menu;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
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
