package ua.foxminded.javaspring.schoolconsoleapp.generator;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupEntity;

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
        List<GroupEntity> expected = new ArrayList<>();
        expected.add(new GroupEntity("AH-84"));
        expected.add(new GroupEntity("AR-58"));
        expected.add(new GroupEntity("DP-22"));

        List<GroupEntity> actual = new GroupsGenerator(3, new Random(42)).generate();

        assertEquals(expected, actual);
    }
}
