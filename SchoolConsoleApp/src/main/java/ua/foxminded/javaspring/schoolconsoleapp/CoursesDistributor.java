package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.List;
import java.util.Random;

public class CoursesDistributor implements Distributor {
    private List<Student> students;
    private final List<Course> courses;
    private static final int MIN_COUNT_COURSES = 1;
    private static final int MAX_COUNT_COURSES = 3;
    private final Random random = new Random();

    public CoursesDistributor(List<Student> students, List<Course> courses) {
        if (students == null || courses == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.students = students;
        this.courses = courses;
    }

    @Override
    public void distribute() {
        for (Student student : students) {
            int index = 0;
            while (index < getRandomNum(MIN_COUNT_COURSES, MAX_COUNT_COURSES)) {
                int i = random.nextInt(courses.size());
                if (!student.checkIfHasCourse(courses.get(i))) {
                    student.addCourse(courses.get(i));
                    index++;
                }
            }
        }
    }

    private int getRandomNum(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

    public List<Student> getStudents() {
        return students;
    }
}
