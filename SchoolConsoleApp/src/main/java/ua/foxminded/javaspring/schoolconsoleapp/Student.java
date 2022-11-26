package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private int id;
    private final String firstName;
    private final String lastName;
    private Group group;
    private List<Course> courses = new ArrayList<>();

    public Student(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public boolean hasGroup() {
        return group != null;
    }

    public boolean hasCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        return courses.contains(course);
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        courses.add(course);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Group getGroup() {
        return group;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.group = group;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courses, firstName, group, lastName, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        return Objects.equals(courses, other.courses) && Objects.equals(firstName, other.firstName)
                && Objects.equals(group, other.group) && Objects.equals(lastName, other.lastName) && id == other.id;
    }

    @Override
    public String toString() {
        String format = "student ID: %-3d| first name: %-8s| last name: %-8s| group: %s";
        if (group == null) {
            return String.format(format, id, firstName, lastName, "no group");
        }
        return String.format(format, id, firstName, lastName, group.getName());
    }
}
