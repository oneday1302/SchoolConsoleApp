package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import java.util.Random;

public class GroupsDistributor implements Distributor<List<Student>> {
    private List<Student> students;
    private final List<Group> groups;
    private static final int MIN_COUNT_STUDENTS = 10;
    private static final int MAX_COUNT_STUDENTS = 30;
    private static final Random random = new Random();

    public GroupsDistributor(List<Student> students, List<Group> groups) {
        if (students == null || groups == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.students = students;
        this.groups = groups;
    }

    @Override
    public List<Student> distribute() {
        for (Group group : groups) {
            int index = 0;
            while (index < getRandomNum(MIN_COUNT_STUDENTS, MAX_COUNT_STUDENTS)) {
                int i = random.nextInt(students.size());
                if (!students.get(i).hasGroup()) {
                    students.get(i).setGroup(group);
                    index++;
                }
            }
        }
        return students;
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
