package ua.foxminded.javaspring.schoolconsoleapp;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class GroupsDistributorTest {

    @Test
    void GroupsDistributor_shouldReturnIllegalArgumentException_whenInputFirstParamNull() {
        List<Group> groups = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new GroupsDistributor(null, groups);
        });
    }
    
    @Test
    void GroupsDistributor_shouldReturnIllegalArgumentException_whenInputSecondParamNull() {
        List<Student> students = new ArrayList<>();
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
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        students.add(new Student("Michael", "Taylor"));
        
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("QH-42"));
        groups.add(new Group("UC-36"));
        groups.add(new Group("FV-33"));
        
        Student student1 = new Student("Jacob", "Smith");
        Student student2 = new Student("Emily", "Jones");
        Student student3 = new Student("Michael", "Taylor");
        
        List<Student> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);
        
        GroupsDistributor actual = new GroupsDistributor(students, groups, new Random(42), 4, 5);
        
        assertEquals(expected, actual.distribute());
    }
    
    @Test
    void distribute_shouldReturnListOfStudentsWithGroup_whenInputNormal() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        students.add(new Student("Michael", "Taylor"));
        
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("QH-42"));
        groups.add(new Group("UC-36"));
        groups.add(new Group("FV-33"));
        
        Student student1 = new Student("Jacob", "Smith");
        student1.setGroup(new Group("QH-42"));
        Student student2 = new Student("Emily", "Jones");
        student2.setGroup(new Group("UC-36"));
        Student student3 = new Student("Michael", "Taylor");
        student3.setGroup(new Group("QH-42"));
        
        List<Student> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);
        
        GroupsDistributor actual = new GroupsDistributor(students, groups, new Random(42), 1, 2);
        
        assertEquals(expected, actual.distribute());
    }
}
