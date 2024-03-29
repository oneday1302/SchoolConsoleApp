package ua.foxminded.javaspring.schoolconsoleapp.distributor;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

class CoursesDistributorTest {

    @Test
    void CoursesDistributor_shouldReturnIllegalArgumentException_whenInputFirstParamNull() {
        List<Course> courses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new CoursesDistributor(null, courses);
        });
    }

    @Test
    void CoursesDistributor_shouldReturnIllegalArgumentException_whenInputSecondParamNull() {
        List<Student> students = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new CoursesDistributor(students, null);
        });
    }

    @Test
    void CoursesDistributor_shouldReturnIllegalArgumentException_whenInputAllParamsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CoursesDistributor(null, null);
        });
    }

    @Test
    void distribute_shouldReturnListOfStudentsWithCourses_whenInputNormal() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        students.add(new Student("Michael", "Taylor"));

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Mathematics", "Mathematics"));
        courses.add(new Course("Biology", "Biology"));
        courses.add(new Course("Economics", "Economics"));

        Student student1 = new Student("Jacob", "Smith");
        student1.addCourse(new Course("Mathematics", "Mathematics"));
        student1.addCourse(new Course("Economics", "Economics"));
        student1.addCourse(new Course("Biology", "Biology"));
        Student student2 = new Student("Emily", "Jones");
        student2.addCourse(new Course("Economics", "Economics"));
        student2.addCourse(new Course("Biology", "Biology"));
        student2.addCourse(new Course("Mathematics", "Mathematics"));
        Student student3 = new Student("Michael", "Taylor");
        student3.addCourse(new Course("Economics", "Economics"));

        List<Student> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);

        CoursesDistributor actual = new CoursesDistributor(students, courses, 1, 3, new Random(42));

        assertEquals(expected, actual.distribute());
    }
}
