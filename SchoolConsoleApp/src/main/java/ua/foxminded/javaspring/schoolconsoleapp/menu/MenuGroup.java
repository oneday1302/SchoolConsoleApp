package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.ArrayList;
import java.util.List;
import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;

public class MenuGroup implements Menu {
    private List<Menu> listMenu = new ArrayList<>();
    private final String name;

    public MenuGroup(String name) {
        this.name = name;
    }

    public void addMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        listMenu.add(menu);
    }

    public void removeMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        listMenu.remove(menu);
    }

    @Override
    public void execute() {
        for (int i = 0; i < listMenu.size(); i++) {
            System.out.println(String.format("%d: %s", i + 1, listMenu.get(i).getName()));
        }
        ConsoleInput input = new ConsoleInput();
        System.out.print("Enter the choice: ");
        listMenu.get(input.getInt() - 1).execute();
    }

    @Override
    public String getName() {
        return name;
    }
}
