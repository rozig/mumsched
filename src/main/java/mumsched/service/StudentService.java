package mumsched.service;

import mumsched.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    List<Student> findAllExcept(Long id);
    Student save(Student student);
    Student findOne(Long id);
    void delete(Long id);
}
