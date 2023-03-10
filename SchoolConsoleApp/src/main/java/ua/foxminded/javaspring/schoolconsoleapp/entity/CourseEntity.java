package ua.foxminded.javaspring.schoolconsoleapp.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "students")
@Entity
@Table(name = "courses", schema = "school")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int id;

    @Column(name = "course_name")
    private String name;

    @Column(name = "course_description")
    private String desc;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "students_courses", schema = "school", 
               joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"), 
               inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id"))
    private Set<StudentEntity> students = new HashSet<>();
    
    public CourseEntity(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
    
    public CourseEntity(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "course ID: " + id + "| course: " + name;
    }
}
