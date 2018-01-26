package mumsched.service;

import mumsched.entity.Course;
import mumsched.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAllExcept(Long id) {
        return courseRepository.findAllExcept(id);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findOne(Long id) {
        return courseRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        courseRepository.delete(id);
    }
}
