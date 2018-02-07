package mumsched.repository;

import java.util.List;

import org.hibernate.annotations.SQLDeleteAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Faculty;
import mumsched.entity.Section;
import mumsched.entity.Student;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s FROM Section s WHERE s.id <> :id")
    public List<Section> findAllExcept(@Param("id") Long id);
    
    @SQLDeleteAll(sql = "truncate table section")
    public void deleteAll();
    
    List<Section> findByBlock(Block block);
    List<Section> findByBlockAndEnrolledStudents(Block block, Student student);
    List<Section> findByCourseAndEnrolledStudents(Course course, Student student);
    List<Section> findByFaculty(Faculty faculty);
    
    List<Section> findByFacultyAndCourseAndBlock(Faculty faculty, Course course, Block block);
    
    
    @Query(value="SELECT s.* FROM section s order by s.block_id",nativeQuery=true)
    public List<Section> findAllOrderByBlock();
}
