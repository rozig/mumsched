package mumsched.service;

import mumsched.entity.Block;
import mumsched.entity.Student;
import mumsched.entity.User;

import java.util.List;

public interface StudentService {

    List<Student> findAll();
    Student save(Student student);
    Student findOne(Long id);
    void delete(Long id);
    Student findByUser(User user);
}
