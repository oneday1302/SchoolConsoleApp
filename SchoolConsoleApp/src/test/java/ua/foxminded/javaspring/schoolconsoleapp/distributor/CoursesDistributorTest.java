package ua.foxminded.javaspring.schoolconsoleapp.distributor;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

class CoursesDistributorTest {

    @Test
    void CoursesDistributor_shouldReturnIllegalArgumentException_whenInputFirstParamNull() {
        List<CourseEntity> courses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new CoursesDistributor(null, courses);
        });
    }

    @Test
    void CoursesDistributor_shouldReturnIllegalArgumentException_whenInputSecondParamNull() {
        List<StudentEntity> students = new ArrayList<>();
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
        List<StudentEntity> students = new ArrayList<>();
        students.add(new StudentEntity("Jacob", "Smith"));
        students.add(new StudentEntity("Emily", "Jones"));
        students.add(new StudentEntity("Michael", "Taylor"));

        List<CourseEntity> courses = new ArrayList<>();
        courses.add(new CourseEntity("Mathematics", "Mathematics"));
        courses.add(new CourseEntity("Biology", "Biology"));
        courses.add(new CourseEntity("Economics", "Economics"));

        StudentEntity student1 = new StudentEntity("Jacob", "Smith");
        student1.addCourse(new CourseEntity("Mathematics", "Mathematics"));
        student1.addCourse(new CourseEntity("Economics", "Economics"));
        student1.addCourse(new CourseEntity("Biology", "Biology"));
        StudentEntity student2 = new StudentEntity("Emily", "Jones");
        student2.addCourse(new CourseEntity("Economics", "Economics"));
        student2.addCourse(new CourseEntity("Biology", "Biology"));
        student2.addCourse(new CourseEntity("Mathematics", "Mathematics"));
        StudentEntity student3 = new StudentEntity("Michael", "Taylor");
        student3.addCourse(new CourseEntity("Economics", "Economics"));

        List<StudentEntity> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);
        expected.add(student3);

        CoursesDistributor actual = new CoursesDistributor(students, courses, 1, 3, new Random(42));

        assertEquals(expected, actual.distribute());
    }
}
