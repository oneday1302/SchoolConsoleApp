package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StudentsGeneratorTest {

    @Test
    void StudentsGenerator_shouldReturnIllegalArgumentException_whenInputFirstParamNull() {
        DataSource<String> mockDataSource = Mockito.mock(DataSource.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentsGenerator(null, mockDataSource, 5);
        });
    }

    @Test
    void StudentsGenerator_shouldReturnIllegalArgumentException_whenInputSecondParamNull() {
        DataSource<String> mockDataSource = Mockito.mock(DataSource.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentsGenerator(mockDataSource, null, 5);
        });
    }

    @Test
    void StudentsGenerator_shouldReturnIllegalArgumentException_whenInputThirdParamLessThanZero() {
        DataSource<String> mockDataSource = Mockito.mock(DataSource.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentsGenerator(mockDataSource, mockDataSource, -1);
        });
    }

    @Test
    void StudentsGenerator_shouldReturnIllegalArgumentException_whenInputThirdParamEqualsZero() {
        DataSource<String> mockDataSource = Mockito.mock(DataSource.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentsGenerator(mockDataSource, mockDataSource, 0);
        });
    }

    @Test
    void generate_shouldReturnListOfStudents_whenInputNormal() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student("Michael", "Smith"));
        expected.add(new Student("Jacob", "Taylor"));
        expected.add(new Student("Jacob", "Jones"));

        Stream<String> streamFirstName = Stream.of("Jacob", "Emily", "Michael");
        DataSource<String> mockFirstName = Mockito.mock(DataSource.class);
        when(mockFirstName.getData()).thenReturn(streamFirstName);

        Stream<String> streamLastName = Stream.of("Smith", "Jones", "Taylor");
        DataSource<String> mockLastName = Mockito.mock(DataSource.class);
        when(mockLastName.getData()).thenReturn(streamLastName);

        List<Student> actual = new StudentsGenerator(mockFirstName, mockLastName, 3, new Random(42)).generate();

        assertEquals(expected, actual);
    }
}
