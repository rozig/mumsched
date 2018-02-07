package mumsched.repository;

import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Section;
import mumsched.entity.Student;

import org.hibernate.annotations.SQLDeleteAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s FROM Section s WHERE s.id <> :id")
    public List<Section> findAllExcept(@Param("id") Long id);
    
    @SQLDeleteAll(sql = "truncate table section")
    public void deleteAll();
    
    List<Section> findByBlock(Block block);
    List<Section> findByBlockAndEnrolledStudents(Block block, Student student);
    List<Section> findByCourseAndEnrolledStudents(Course course, Student student);
}
