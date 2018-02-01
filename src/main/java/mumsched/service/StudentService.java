package mumsched.service;

import mumsched.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student save(Student student);
    Student findOne(Long id);
    void delete(Long id);
}
