package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput {
    private static final Scanner scanner = new Scanner(System.in);

    public String getLine() {
        return scanner.nextLine();
    }

    public int getInt() {
        return scanner.nextInt();
    }
}
