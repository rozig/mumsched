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
    
    @Query(value = "SELECT c1.* FROM course c1 INNER JOIN course c2 ON c1.id = c2.prereq_course_id WHERE c2.prereq_course_id >0"
    		,nativeQuery=true)
    public List<Course> findPreRequisiteCourses();
    
    @Query(value = "SELECT c.* FROM course c WHERE not exists(select 1 from course c1 where c1.prereq_course_id=c.id)"
    		,nativeQuery=true)
    public List<Course> findCourses();
    
    public List<Course> findByPreRequisiteNotNull();
}
