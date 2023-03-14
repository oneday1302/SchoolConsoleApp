package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;
import ua.foxminded.javaspring.schoolconsoleapp.mapper.StudentMapper;

@Repository
@Profile("JDBCTemplate")
public class StudentDaoJDBC implements StudentDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        String sql = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?)";
        jdbc.update(sql, student.getFirstName(), student.getLastName());
    }

    @Override
    public void addAll(List<Student> students) {
        if (students == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }

        String sql = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?)";
        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, students.get(i).getFirstName());
                ps.setString(2, students.get(i).getLastName());
            }

            public int getBatchSize() {
                return students.size();
            }
        });
    }

    @Override
    public List<Student> getAll() {
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT school.students.student_id, school.students.group_id, school.groups.group_name, first_name, last_name")
           .add("FROM school.students")
           .add("LEFT JOIN school.groups")
           .add("ON school.students.group_id = school.groups.group_id")
           .add("ORDER BY school.students.student_id");
        return jdbc.query(sql.toString(), new StudentMapper());
    }

    @Override
    public void updateGroupIdRow(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        if (student.getGroup() == null) {
            String sql = "UPDATE school.students SET group_id = NULL WHERE student_id = ?";
            jdbc.update(sql, student.getId());
        } else {
            String sql = "UPDATE school.students SET group_id = ? WHERE student_id = ?";
            jdbc.update(sql, student.getGroup().getId(), student.getId());
        }
    }

    @Override
    public List<Student> findAllStudentsInTheCourse(String courseName) {
        if (courseName == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        StringJoiner sql = new StringJoiner(" ");
        sql.add("SELECT school.students.student_id, school.students.group_id, school.groups.group_name, first_name, last_name")
           .add("FROM school.students")
           .add("LEFT JOIN school.groups")
           .add("ON school.students.group_id = school.groups.group_id")
           .add("JOIN school.students_courses")
           .add("ON school.students.student_id = school.students_courses.student_id")
           .add("JOIN school.courses")
           .add("ON school.students_courses.course_id = school.courses.course_id")
           .add("WHERE school.courses.course_name = ?")
           .add("ORDER BY school.students.student_id");
        return jdbc.query(sql.toString(), new Object[] { courseName }, new StudentMapper());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM school.students WHERE student_id = ?";
        jdbc.update(sql, id);
    }

    @Override
    public void addStudentToCourse(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }

        String sql = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?)";
        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, student.getId());
                ps.setInt(2, student.getCourses().get(i).getId());
            }

            public int getBatchSize() {
                return student.getCourses().size();
            }
        });
    }

    @Override
    public void removeStudentFromCourses(int studentId) {
        String sql = "DELETE FROM school.students_courses WHERE student_id = ?";
        jdbc.update(sql, studentId);
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        String sql = "DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?";
        jdbc.update(sql, studentId, courseId);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        String sql = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        jdbc.update(sql, studentId, courseId);
    }

    @Override
    public boolean isEmpty() {
        String sql = "SELECT COUNT(student_id) FROM school.students";
        int count = jdbc.queryForObject(sql, Integer.class);
        return count == 0;
    }
}
