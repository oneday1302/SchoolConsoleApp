package ua.foxminded.javaspring.schoolconsoleapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.foxminded.javaspring.schoolconsoleapp.dao.CourseDao;

@Service
public class CourseServiceImpl<T> implements CourseService<T> {

    private final CourseDao<T> courseDao;

    public CourseServiceImpl(CourseDao<T> courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void add(T course) {
        if (course == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        courseDao.add(course);
    }

    @Override
    public void addAll(List<T> courses) {
        if (courses == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        
        courseDao.addAll(courses);
    }

    @Override
    public List<T> getAll() {
        return courseDao.getAll();
    }

    @Override
    public boolean isEmpty() {
        return courseDao.isEmpty();
    }
}
