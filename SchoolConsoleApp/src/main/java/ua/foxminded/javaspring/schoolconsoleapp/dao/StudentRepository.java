package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Course;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    @Query("select s from Student s join s.courses c where c.name =:courseName")
    public List<Student> findAllStudentsInTheCourse(@Param("courseName") String courseName);
    
    @Query("select c from Course c where c.id =:courseId")
    public Course getCourse(@Param("courseId") int courseId);
    
    @Query("select count(id) from Student")
    public int countOfStudents();
}
