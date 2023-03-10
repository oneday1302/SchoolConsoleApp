package ua.foxminded.javaspring.schoolconsoleapp.entity;

import java.util.Objects;

public class Group {
    
    private int id;
    private final String name;

    public Group(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.name = name;
    }

    public Group(int id, String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
        return id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        String format = "ID: %-2d| group name: %s";
        return String.format(format, id, name);
    }
}
