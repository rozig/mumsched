package mumsched.repository;

import mumsched.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.id <> :id")
    public List<Course> findAllExcept(@Param("id") Long id);
    
    @Query("SELECT c FROM Course c WHERE c.code = :code")
    public Course findByCode(@Param("code") String code);
    
    @Query("SELECT c1.* FROM Course c1,Course c2 WHERE c1.id = c2.preRequisite and c2.preRequisite >0")
    public List<Course> findPreRequisiteCourses();
    
    @Query("ELECT c1.* FROM Course c1,Course c2 WHERE c1.id <> c2.preRequisite order by c1.code,c1.preRequisite desc")
    public List<Course> findCourses();
    
    public List<Course> findByPreRequisiteNotNull();
}
