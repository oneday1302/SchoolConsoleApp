package ua.foxminded.javaspring.schoolconsoleapp.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.schoolconsoleapp.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    
    @Query("select g from Group g join Student s on g.id = s.id group by g.id having count(s.id) <=:studentsNumber order by g.id")
    public List<Group> getAllGrupsWithLessOrEqualsStudentsNumber(@Param("studentsNumber") long studentsNumber);
}
