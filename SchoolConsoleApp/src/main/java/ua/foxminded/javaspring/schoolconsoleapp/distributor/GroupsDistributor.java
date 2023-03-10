package ua.foxminded.javaspring.schoolconsoleapp.distributor;

import java.util.List;
import java.util.Random;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

public class GroupsDistributor implements Distributor<StudentEntity> {
    private static final int MIN_COUNT_STUDENTS = 10;
    private static final int MAX_COUNT_STUDENTS = 30;
    private List<StudentEntity> students;
    private final List<GroupEntity> groups;
    private final Random random;
    private final int min;
    private final int max;

    public GroupsDistributor(List<StudentEntity> students, List<GroupEntity> groups) {
        this(students, groups, new Random(), MIN_COUNT_STUDENTS, MAX_COUNT_STUDENTS);
    }

    GroupsDistributor(List<StudentEntity> students, List<GroupEntity> groups, Random random, int min, int max) {
        if (students == null || groups == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.students = students;
        this.groups = groups;
        this.random = random;
        this.min = min;
        this.max = max;
    }

    @Override
    public List<StudentEntity> distribute() {
        int numberOfStudentsWithoutGroup = students.size();
        for (GroupEntity group : groups) {
            if (numberOfStudentsWithoutGroup < min) {
                return students;
            }
            int index = 0;
            int tempMax = Math.min(max, numberOfStudentsWithoutGroup);
            while (index < getRandomNum(min, tempMax)) {
                int i = random.nextInt(students.size());
                if (!students.get(i).hasGroup()) {
                    students.get(i).setGroup(group);
                    index++;
                    numberOfStudentsWithoutGroup--;
                }
            }
        }
        return students;
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
