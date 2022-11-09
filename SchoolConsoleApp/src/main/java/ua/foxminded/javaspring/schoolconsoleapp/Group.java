package ua.foxminded.javaspring.schoolconsoleapp;

import java.util.Objects;

public class Group {
    private final int groupID;
    private final String groupName;

    public Group(int groupID, String groupName) {
        if (groupName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupID, groupName);
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
        return groupID == other.groupID && Objects.equals(groupName, other.groupName);
    }
}
