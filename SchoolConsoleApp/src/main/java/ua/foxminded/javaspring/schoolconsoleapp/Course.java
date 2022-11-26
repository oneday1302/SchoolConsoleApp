package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.Objects;

public class Course {
    private int id;
    private final String name;
    private final String desc;

    public Course(String name, String desc) {
        if (name == null || desc == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.name = name;
        this.desc = desc;
    }

    public Course(int id, String name, String desc) {
        if (name == null || desc == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int hashCode() {
        return Objects.hash(desc, id, name);
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
        return Objects.equals(desc, other.desc) && id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "course ID: " + id + "| course: " + name;
    }
}
