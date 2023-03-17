package ua.foxminded.javaspring.schoolconsoleapp.entity;

import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "courses")
@Entity
@Table(name = "students", schema = "school")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "students_courses", schema = "school", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"))
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

    public boolean hasGroup() {
        return group != null;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public void removeStudentFromCourses() {
        courses.clear();
    }

    public void removeStudentFromCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        courses.remove(course);
    }

    public void removeStudentFromCourse(int courseId) {
        for(Course course : courses) {
            if (course.getId() == courseId) {
                courses.remove(course);
                break;
            }
        }
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
