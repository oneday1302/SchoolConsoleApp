package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import java.util.Random;

public class CoursesDistributor implements Distributor<Student> {
    private static final int DEFAULTE_MIN_COUNT_COURSES = 1;
    private static final int DEFAULTE_MAX_COUNT_COURSES = 3;
    private List<Student> students;
    private final List<Course> courses;
    private final Random random;
    private final int minCountCurses;
    private final int maxCountCurses;
    
    public CoursesDistributor(List<Student> students, List<Course> courses) {
        this(students, courses, DEFAULTE_MIN_COUNT_COURSES, DEFAULTE_MAX_COUNT_COURSES, new Random());
    }

    public CoursesDistributor(List<Student> students, List<Course> courses, int minCountCurses, int maxCountCurses) {
        this(students, courses, minCountCurses, maxCountCurses, new Random());
    }

    CoursesDistributor(List<Student> students, List<Course> courses, int minCountCurses, int maxCountCurses, Random random) {
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
    public List<Student> distribute() {
        for (Student student : students) {
            int index = 0;
            while (index < getRandomNum(minCountCurses, maxCountCurses)) {
                int i = random.nextInt(courses.size());
                if (!student.hasCourse(courses.get(i))) {
                    student.addCourse(courses.get(i));
                    index++;
                }
            }
        }
        return students;
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }
}
