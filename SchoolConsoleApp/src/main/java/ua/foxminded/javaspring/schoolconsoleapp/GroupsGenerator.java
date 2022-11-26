package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupsGenerator implements Generator<Group> {
    private final int count;
    private static final Random random = new Random();

    public GroupsGenerator(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Param cannot be less or equals zero.");
        }
        this.count = count;
    }

    @Override
    public List<Group> generate() {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            groups.add(new Group(generateGroupName()));
        }
        return groups;
    }

    private String generateGroupName() {
        StringBuilder groupName = new StringBuilder();
        return groupName.append((char)getRandomNum('A', 'Z'))
                        .append((char)getRandomNum('A', 'Z'))
                        .append('-')
                        .append(getRandomNum(0, 9))
                        .append(getRandomNum(0, 9))
                        .toString();
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
