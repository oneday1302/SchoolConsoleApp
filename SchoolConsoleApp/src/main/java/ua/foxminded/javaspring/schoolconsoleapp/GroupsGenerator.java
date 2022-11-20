package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupsGenerator implements Generator<Group> {
    private static final String DATA_NUMBER = "0123456789";
    private static final int NUMBER_LENGTH = 2;
    private static final String DATA_SYMBOL = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final int SYMBOL_LENGTH = 2;
    private final int count;

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
        return groupName
                .append(cycle(DATA_SYMBOL, SYMBOL_LENGTH))
                .append('-')
                .append(cycle(DATA_NUMBER, NUMBER_LENGTH))
                .toString();
    }

    private StringBuilder cycle(String data, int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(data.charAt(random.nextInt(data.length())));
        }
        return result;
    }
}
