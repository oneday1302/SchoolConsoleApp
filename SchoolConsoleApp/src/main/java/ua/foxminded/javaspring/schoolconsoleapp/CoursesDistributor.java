package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import java.util.Random;

public class CoursesDistributor implements Distributor<Student> {
    private List<Student> students;
    private final List<Course> courses;
    private final Random random;
    private static final int MIN_COUNT_COURSES = 1;
    private static final int MAX_COUNT_COURSES = 3;

    public CoursesDistributor(List<Student> students, List<Course> courses) {
        this(students, courses, new Random());
    }
    
    public CoursesDistributor(List<Student> students, List<Course> courses, Random random) {
        if (students == null || courses == null || random == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.students = students;
        this.courses = courses;
        this.random = random;
    }

    @Override
    public List<Student> distribute() {
        for (Student student : students) {
            int index = 0;
            while (index < getRandomNum(MIN_COUNT_COURSES, MAX_COUNT_COURSES)) {
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
