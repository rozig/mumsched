package mumsched.service;

import mumsched.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course save(Course course);
    Course findOne(Long id);
    void delete(Long id);
}
