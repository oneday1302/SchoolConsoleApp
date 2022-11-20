package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.Objects;

public class Course {
    private int courseID;
    private final String courseName;
    private final String courseDescription;

    public Course(String courseName, String courseDescription) {
        if (courseName == null || courseDescription == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Course(int courseID, String courseName, String courseDescription) {
        if (courseName == null || courseDescription == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseDescription, courseID, courseName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        return Objects.equals(courseDescription, other.courseDescription) && courseID == other.courseID
                && Objects.equals(courseName, other.courseName);
    }

    @Override
    public String toString() {
        return "course ID: " + courseID + "| course: " + courseName;
    }
}
