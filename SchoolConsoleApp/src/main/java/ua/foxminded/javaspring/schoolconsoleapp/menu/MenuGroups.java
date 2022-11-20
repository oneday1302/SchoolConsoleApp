package ua.foxminded.javaspring.schoolconsoleapp.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import ua.foxminded.javaspring.schoolconsoleapp.ConsoleInput;

public class MenuGroups implements Menu {
    private List<Menu> listMenu = new ArrayList<>();

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
        System.out.println(getName());
        ConsoleInput input = new ConsoleInput();
        System.out.print("Enter the choice: ");
        listMenu.get(input.getInt() - 1).execute();
    }

    @Override
    public String getName() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        for (int i = 0; i < listMenu.size(); i++) {
            result.add(String.format("%d: %s", i + 1, listMenu.get(i).getName()));
        }
        return result.toString();
    }
}
