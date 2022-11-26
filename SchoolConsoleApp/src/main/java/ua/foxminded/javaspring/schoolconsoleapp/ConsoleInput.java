package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.Scanner;

public class ConsoleInput {

    public String getLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int getInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
