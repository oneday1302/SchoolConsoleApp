package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.schoolconsoleapp.Course;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public void add(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        courseDao.add(course);
    }

    @Override
    public void addAll(List<Course> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        courseDao.addAll(courses);
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public boolean isEmpty() {
        return courseDao.isEmpty();
    }
}
