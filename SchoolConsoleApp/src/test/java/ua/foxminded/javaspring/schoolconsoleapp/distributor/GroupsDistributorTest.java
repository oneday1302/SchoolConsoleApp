package ua.foxminded.javaspring.schoolconsoleapp.distributor;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ua.foxminded.javaspring.schoolconsoleapp.entity.GroupEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

class GroupsDistributorTest {

    @Test
    void GroupsDistributor_shouldReturnIllegalArgumentException_whenInputFirstParamNull() {
        List<GroupEntity> groups = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupsDistributor(null, groups);
        });
    }

    @Test
    void GroupsDistributor_shouldReturnIllegalArgumentException_whenInputSecondParamNull() {
        List<StudentEntity> students = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupsDistributor(students, null);
        });
    }

    @Test
    void GroupsDistributor_shouldReturnIllegalArgumentException_whenInputAllParamsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupsDistributor(null, null);
        });
    }

    @Test
    void distribute_shouldReturnListOfStudentsWithGroup_whenInputNumberOfStudentsLessThanMinNumber() {
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity("Jacob", "Smith"));
        students.add(new StudentEntity("Emily", "Jones"));
        students.add(new StudentEntity("Michael", "Taylor"));

        List<GroupEntity> groups = new ArrayList<>();
        groups.add(new GroupEntity("QH-42"));
        groups.add(new GroupEntity("UC-36"));
        groups.add(new GroupEntity("FV-33"));

        StudentEntity student1 = new StudentEntity("Jacob", "Smith");
        StudentEntity student2 = new StudentEntity("Emily", "Jones");
        StudentEntity student3 = new StudentEntity("Michael", "Taylor");

        List<StudentEntity> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);

        GroupsDistributor actual = new GroupsDistributor(students, groups, new Random(42), 4, 5);

        assertEquals(expected, actual.distribute());
    }

    @Test
    void distribute_shouldReturnListOfStudentsWithGroup_whenInputNormal() {
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity("Jacob", "Smith"));
        students.add(new StudentEntity("Emily", "Jones"));
        students.add(new StudentEntity("Michael", "Taylor"));

        List<GroupEntity> groups = new ArrayList<>();
        groups.add(new GroupEntity("QH-42"));
        groups.add(new GroupEntity("UC-36"));
        groups.add(new GroupEntity("FV-33"));

        StudentEntity student1 = new StudentEntity("Jacob", "Smith");
        student1.setGroup(new GroupEntity("QH-42"));
        StudentEntity student2 = new StudentEntity("Emily", "Jones");
        student2.setGroup(new GroupEntity("UC-36"));
        StudentEntity student3 = new StudentEntity("Michael", "Taylor");
        student3.setGroup(new GroupEntity("QH-42"));

        List<StudentEntity> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);

        GroupsDistributor actual = new GroupsDistributor(students, groups, new Random(42), 1, 2);

        assertEquals(expected, actual.distribute());
    }
}
