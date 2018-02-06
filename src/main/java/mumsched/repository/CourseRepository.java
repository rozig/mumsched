package mumsched.repository;

import mumsched.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.id <> :id")
    public List<Course> findAllExcept(@Param("id") Long id);
    
    public List<Course> findByPreRequisiteNotNull();
}
