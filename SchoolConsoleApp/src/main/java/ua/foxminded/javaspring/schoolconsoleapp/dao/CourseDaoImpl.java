package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

@Repository
@Profile("nativeJDBC")
public class CourseDaoImpl implements CourseDao<Course> {
    private final DataSource dataSource;

    public CourseDaoImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, course.getName());
            statement.setString(2, course.getDesc());
            statement.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAll(List<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO school.courses (course_name, course_description) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            for (Course course : courses) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getDesc());
                statement.addBatch();
            }
            statement.executeBatch();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM school.courses ORDER BY course_id");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                courses.add(new Course(result.getInt("course_id"), result.getString("course_name"), result.getString("course_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public boolean isEmpty() {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT course_id FROM school.courses LIMIT 1");
            ResultSet result = statement.executeQuery();
            return !result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
