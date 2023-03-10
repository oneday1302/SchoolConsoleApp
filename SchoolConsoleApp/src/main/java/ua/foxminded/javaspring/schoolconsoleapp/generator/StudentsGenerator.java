package ua.foxminded.javaspring.schoolconsoleapp.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import ua.foxminded.javaspring.schoolconsoleapp.DataSource;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

public class StudentsGenerator implements Generator<StudentEntity> {
    private final DataSource<String> firstName;
    private final DataSource<String> lastName;
    private final int count;
    private final Random random;

    public StudentsGenerator(DataSource<String> firstName, DataSource<String> lastName, int count) {
        this(firstName, lastName, count, new Random());
    }
    
    StudentsGenerator(DataSource<String> firstName, DataSource<String> lastName, int count, Random random) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Param cannot be less or equals zero.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.count = count;
        this.random = random;
    }

    @Override
    public List<StudentEntity> generate() {
        List<String> firstNameList = firstName.getData().collect(Collectors.toList());
        List<String> lastNameList = lastName.getData().collect(Collectors.toList());
        List<StudentEntity> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            students.add(
                    new StudentEntity(
                            firstNameList.get(random.nextInt(firstNameList.size())),
                            lastNameList.get(random.nextInt(lastNameList.size()))));
        }
        return students;
    }
}
