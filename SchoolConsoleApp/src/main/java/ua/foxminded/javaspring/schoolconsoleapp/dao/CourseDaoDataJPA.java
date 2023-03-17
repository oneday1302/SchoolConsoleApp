package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

@Repository
@Profile("DataJPA")
public class CourseDaoDataJPA implements CourseDao {
    
    private final CourseRepository repository;
    
    public CourseDaoDataJPA(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        repository.save(course);
    }

    @Override
    public void addAll(List<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        repository.saveAll(courses);
    }

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public boolean isEmpty() {
        return repository.countOfCourses() == 0;
    }
}
