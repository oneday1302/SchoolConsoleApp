package ua.foxminded.javaspring.schoolconsoleapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
   
}
