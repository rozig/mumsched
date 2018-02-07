package mumsched.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mumsched.entity.Block;
import mumsched.entity.Course;
import mumsched.entity.Faculty;
import mumsched.entity.Section;
import mumsched.entity.Student;
import mumsched.repository.SectionRepository;

@Service("sectionService")
public class SectionServiceImpl implements SectionService {
    @Autowired
    SectionRepository sectionRepository;

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }
    
    @Override
    public List<Section> findAllOrderByBlock() {
        return sectionRepository.findAllOrderByBlock();
    }

    @Override
    public List<Section> findAllExcept(Long id) {
        return sectionRepository.findAllExcept(id);
    }

    @Override
    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section findOne(Long id) {
        return sectionRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        sectionRepository.delete(id);
    }
    
    @Override
    public List<Section> findByBlock(Block block){
    	return sectionRepository.findByBlock(block);
    }
    
    @Override
	public List<Section> findByBlockAndEnrolledStudents(Block block, Student student) {
		return sectionRepository.findByBlockAndEnrolledStudents(block, student);
	}
    
	@Override
	public List<Section> findByCourseAndEnrolledStudents(Course course, Student student) {
		return sectionRepository.findByCourseAndEnrolledStudents(course, student);
	}

	@Override
	public List<Section> findByFaculty(Faculty faculty) {
		return sectionRepository.findByFaculty(faculty);
	}

	@Override
	public List<Section> findByFacultyAndCourseAndBlock(Faculty faculty, Course course, Block block) {
		return sectionRepository.findByFacultyAndCourseAndBlock(faculty, course, block);
	}
    
    
}
