package ua.foxminded.javaspring.schoolconsoleapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "groups", schema = "school")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @Column(name = "group_name")
    private String name;

    public GroupEntity(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        String format = "ID: %-2d| group name: %s";
        return String.format(format, id, name);
    }
}
