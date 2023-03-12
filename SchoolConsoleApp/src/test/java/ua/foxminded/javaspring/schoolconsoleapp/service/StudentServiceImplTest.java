package ua.foxminded.javaspring.schoolconsoleapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.javaspring.schoolconsoleapp.configs.ServiceLayerTestConfig;
import ua.foxminded.javaspring.schoolconsoleapp.dao.StudentDao;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@SpringBootTest(classes = ServiceLayerTestConfig.class)
class StudentServiceImplTest {
    
    @Autowired
    StudentDao studentDao;
    
    @Autowired
    StudentService studentService;

    @Test
    void add_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.add(null);
        });
    }

    @Test
    void add__whenInputParamStudent() {
        Student student = new Student("Jacob", "Smith");
        studentService.add(student);
        verify(studentDao, times(1)).add(student);
    }
    
    @Test
    void addAll_shouldReturnIllegalArgumentException_whenInputParamNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.addAll(null);
        });
    }

    @Test
    void addAll__whenInputParamListOfStudent() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        studentService.addAll(students);
        verify(studentDao, times(1)).addAll(students);
    }

    @Test
    void getAll_shouldReturnListOfStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        when(studentDao.getAll()).thenReturn(students);
        assertEquals(students, studentService.getAll());
    }

    @Test
    void updateGroupIdRow_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.updateGroupIdRow(null);
        });
    }

    @Test
    void updateGroupIdRow_whenInputStudent() {
        Student student = new Student("Jacob", "Smith");
        studentService.updateGroupIdRow(student);
        verify(studentDao, times(1)).updateGroupIdRow(student);
    }

    @Test
    void findAllStudentsInTheCourse_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.findAllStudentsInTheCourse(null);
        });
    }

    @Test
    void findAllStudentsInTheCourse_shouldReturnListOfStudents_whenInputCourseName() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Jacob", "Smith"));
        students.add(new Student("Emily", "Jones"));
        when(studentDao.findAllStudentsInTheCourse("History")).thenReturn(students);
        assertEquals(students, studentService.findAllStudentsInTheCourse("History"));
    }

    @Test
    void delete_whenInputStudentId() {
        studentService.delete(1);
        verify(studentDao, times(1)).delete(1);
    }

    @Test
    void addStudentToCourse_shouldReturnIllegalArgumentException_whenInputNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            studentService.addStudentToCourse(null);
        });
    }

    @Test
    void addStudentToCourse_whenInputStudent() {
        Student student = new Student("Jacob", "Smith");
        studentService.addStudentToCourse(student);
        verify(studentDao, times(1)).addStudentToCourse(student);
    }

    @Test
    void removeStudentFromCourses_whenInputStudentId() {
        studentService.removeStudentFromCourses(1);
        verify(studentDao, times(1)).removeStudentFromCourses(1);
    }

    @Test
    void addStudentToCourse_whenInputStudentIdAndCourseId() {
        studentService.addStudentToCourse(1, 1);
        verify(studentDao, times(1)).addStudentToCourse(1, 1);
    }

    @Test
    void removeStudentFromCourse_whenInputStudentIdAndCourseId() {
        studentService.removeStudentFromCourse(1, 1);
        verify(studentDao, times(1)).removeStudentFromCourse(1, 1);
    }
    
    @Test
    void gisEmpty_shouldReturnTrue_whenTableIsEmpty() {
        when(studentDao.isEmpty()).thenReturn(true);
        assertEquals(true, studentService.isEmpty());
    }
    
    @Test
    void isEmpty_shouldReturnFalse_whenTableIsNotEmpty() {
        when(studentDao.isEmpty()).thenReturn(false);
        assertEquals(false, studentService.isEmpty());
    }
}
