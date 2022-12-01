package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CoursesGeneratorTest {

    @Test
    void CoursesGenerator_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CoursesGenerator(null);
        });
    }

    @Test
    void generate_shouldReturnIllegalStateException_whenInputIncorrectFormatData() {
        Stream<String> stream = Stream.of("Mathematics");
        DataSource<String> mockDataSource = Mockito.mock(DataSource.class);
        when(mockDataSource.getData()).thenReturn(stream);
        CoursesGenerator coursesGenerator = new CoursesGenerator(mockDataSource);
        
        assertThrows(IllegalStateException.class, () -> {
            coursesGenerator.generate();
        });
    }

    @Test
    void generate_shouldReturnListOfCourse_whenInputCourseData() {
        List<Course> expected = new ArrayList<>();
        expected.add(new Course("Mathematics", "Mathematics"));
        expected.add(new Course("Biology", "Biology"));

        Stream<String> stream = Stream.of("Mathematics_Mathematics", "Biology_Biology");
        DataSource<String> mockDataSource = Mockito.mock(DataSource.class);
        when(mockDataSource.getData()).thenReturn(stream);

        List<Course> actual = new CoursesGenerator(mockDataSource).generate();
        assertEquals(expected, actual);
    }
}