package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private int studentID;
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

    public Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStudentID() {
        return studentID;
    }

    public boolean ifStudentInGroup() {
        if (group == null) {
            return false;
        }
        return true;
    }

    public boolean checkIfHasCourse(Course course) {
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
        this.group = group;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courses, firstName, group, lastName, studentID);
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
                && Objects.equals(group, other.group) && Objects.equals(lastName, other.lastName)
                && studentID == other.studentID;
    }

    @Override
    public String toString() {
        String format = "student ID: %-3d| first name: %-8s| last name: %-8s| group: %s";
        if (group == null) {
            return String.format(format, studentID, firstName, lastName, "no group");
        }
        return String.format(format, studentID, firstName, lastName, group.getGroupName());
    }
}
