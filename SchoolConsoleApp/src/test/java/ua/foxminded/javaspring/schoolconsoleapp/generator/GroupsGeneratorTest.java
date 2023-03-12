package ua.foxminded.javaspring.schoolconsoleapp.generator;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

class GroupsGeneratorTest {

    @Test
    void GroupsGenerator_shouldReturnIllegalArgumentException_whenInputNumberLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupsGenerator(-1);
        });
    }

    @Test
    void GroupsGenerator_shouldReturnIllegalArgumentException_whenInputNumberEqualsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupsGenerator(0);
        });
    }

    @Test
    void generate_shouldReturnListOfGroup_whenInputNormal() {
        List<Group> expected = new ArrayList<>();
        expected.add(new Group("AH-84"));
        expected.add(new Group("AR-58"));
        expected.add(new Group("DP-22"));

        List<Group> actual = new GroupsGenerator(3, new Random(42)).generate();

        assertEquals(expected, actual);
    }
}
