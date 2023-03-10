package ua.foxminded.javaspring.schoolconsoleapp.distributor;

import java.util.List;
import java.util.Random;
import ua.foxminded.javaspring.schoolconsoleapp.entity.CourseEntity;
import ua.foxminded.javaspring.schoolconsoleapp.entity.StudentEntity;

public class CoursesDistributor implements Distributor<StudentEntity> {
    private static final int DEFAULTE_MIN_COUNT_COURSES = 1;
    private static final int DEFAULTE_MAX_COUNT_COURSES = 3;
    private List<StudentEntity> students;
    private final List<CourseEntity> courses;
    private final Random random;
    private final int minCountCurses;
    private final int maxCountCurses;
    
    public CoursesDistributor(List<StudentEntity> students, List<CourseEntity> courses) {
        this(students, courses, DEFAULTE_MIN_COUNT_COURSES, DEFAULTE_MAX_COUNT_COURSES, new Random());
    }

    public CoursesDistributor(List<StudentEntity> students, List<CourseEntity> courses, int minCountCurses, int maxCountCurses) {
        this(students, courses, minCountCurses, maxCountCurses, new Random());
    }

    CoursesDistributor(List<StudentEntity> students, List<CourseEntity> courses, int minCountCurses, int maxCountCurses, Random random) {
        if (students == null || courses == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        if (minCountCurses < 0 || maxCountCurses < 0) {
            throw new IllegalStateException();
        }
        this.students = students;
        this.courses = courses;
        this.random = random;
        this.minCountCurses = minCountCurses;
        this.maxCountCurses = maxCountCurses;
    }

    @Override
    public List<StudentEntity> distribute() {
        for (StudentEntity student : students) {
            int countCourses = getRandomNum(minCountCurses, maxCountCurses);
            while (countCourses != 0) {
                int i = random.nextInt(courses.size());
                if (!student.hasCourse(courses.get(i))) {
                    student.addCourse(courses.get(i));
                    countCourses--;
                }
            }
        }
        return students;
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
