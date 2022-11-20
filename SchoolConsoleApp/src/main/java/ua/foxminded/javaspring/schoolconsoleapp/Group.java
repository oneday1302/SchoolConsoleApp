package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private int groupID;
    private final String groupName;
    private List<Student> students = new ArrayList<>();

    public Group(String groupName) {
        if (groupName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.groupName = groupName;
    }

    public Group(int groupID, String groupName) {
        if (groupName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        students.add(student);
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, groupName, students);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        return groupID == other.groupID && Objects.equals(groupName, other.groupName)
                && Objects.equals(students, other.students);
    }

    @Override
    public String toString() {
        return "ID: " + groupID + "| group name: " + groupName;
    }
}
