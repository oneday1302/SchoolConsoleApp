package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoursesGenerator implements Generator<Course> {
    private final DataSource<String> dataSource;
    private static final int KEY = 0;
    private static final int VALUE = 1;

    public CoursesGenerator(DataSource<String> dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.dataSource = dataSource;
    }

    @Override
    public List<Course> generate() {
        List<Course> courses = new ArrayList<>();
        for (Map.Entry<String, String> entry : toMap(dataSource).entrySet()) {
            courses.add(new Course(entry.getKey(), entry.getValue()));
        }
        return courses;
    }

    private Map<String, String> toMap(DataSource<String> dataSource) {
        Map<String, String> map = new HashMap<>();
        for (String line : dataSource.getData().collect(Collectors.toList())) {
            String[] data = line.split("_");
            if (data.length != 2) {
                throw new IllegalStateException();
            }
            map.put(data[KEY], data[VALUE]);
        }
        return map;
    }
}
