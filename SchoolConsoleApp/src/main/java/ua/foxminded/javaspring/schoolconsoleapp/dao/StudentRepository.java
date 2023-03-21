package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    public List<Student> findAllStudentsInTheCourseByCoursesName(String courseName);
}
