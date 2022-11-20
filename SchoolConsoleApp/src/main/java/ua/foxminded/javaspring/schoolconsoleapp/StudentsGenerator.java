package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentsGenerator implements Generator<Student> {
    private static final String[] FIRST_NAME_DATA = { 
            "Jacob", "Emily", "Michael", "Emma", "Joshua", 
            "Madison", "Matthew", "Olivia", "Ethan", "Hannah", 
            "Andrew", "Abigail", "Daniel", "Isabella", "William", 
            "Ashley", "Joseph", "Ashley", "Joseph", "Samantha" };
    private static final String[] LAST_NAME_DATA = { 
            "Smith", "Jones", "Taylor", "Williams", "Brown", 
            "Davies", "Evans", "Wilson", "Thomas", "Roberts", 
            "Johnson", "Lewis", "Walker", "Robinson", "Wood", 
            "Thompson", "White", "Watson", "Jackson", "Wright" };
    private final int count;

    public StudentsGenerator(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Param cannot be less or equals zero.");
        }
        this.count = count;
    }

    @Override
    public List<Student> generate() {
        Random random = new Random();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            students.add(new Student(
                                     FIRST_NAME_DATA[random.nextInt(FIRST_NAME_DATA.length)],
                                     LAST_NAME_DATA[random.nextInt(LAST_NAME_DATA.length)]));
        }
        return students;
    }
}
