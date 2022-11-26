package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StudentsGenerator implements Generator<Student> {
    private final DataSource<String> firstName;
    private final DataSource<String> lastName;
    private final int count;
    private static final Random random = new Random();

    public StudentsGenerator(DataSource<String> firstName, DataSource<String> lastName, int count) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Param cannot be less or equals zero.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.count = count;
    }

    @Override
    public List<Student> generate() {
        List<String> firstNameList = firstName.getData().collect(Collectors.toList());
        List<String> lastNameList = lastName.getData().collect(Collectors.toList());
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            students.add(
                    new Student(
                            firstNameList.get(random.nextInt(firstNameList.size())),
                            lastNameList.get(random.nextInt(lastNameList.size()))));
        }
        return students;
    }
}
