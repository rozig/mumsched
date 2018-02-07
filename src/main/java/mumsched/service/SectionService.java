package mumsched.service;

import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Section;
import mumsched.entity.Student;

import java.util.List;

public interface SectionService {
    List<Section> findAll();
    List<Section> findAllExcept(Long id);
    Section save(Section section);
    Section findOne(Long id);
    void delete(Long id);
    
    List<Section> findByBlock(Block block);
    List<Section> findByBlockAndEnrolledStudents(Block block, Student student);
    List<Section> findByCourseAndEnrolledStudents(Course course, Student student);
}