package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.Scanner;

public class ConsoleInput {
    private static final Scanner scanner = new Scanner(System.in);

    public String getLine() {
        return scanner.nextLine();
    }

    public int getInt() {
        return scanner.nextInt();
    }
}
