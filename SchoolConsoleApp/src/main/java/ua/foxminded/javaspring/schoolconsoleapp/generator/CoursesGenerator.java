package ua.foxminded.javaspring.schoolconsoleapp.generator;

import java.util.List;
import java.util.stream.Collectors;
import ua.foxminded.javaspring.schoolconsoleapp.DataSource;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;

public class CoursesGenerator implements Generator<CourseEntity> {
    private final DataSource<String> dataSource;

    public CoursesGenerator(DataSource<String> dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.dataSource = dataSource;
    }

    @Override
    public List<CourseEntity> generate() {
        return dataSource.getData().map(s -> s.split("_")).map(parts -> new CourseEntity(parts[0], parts[1])).collect(Collectors.toList());
    }
}
