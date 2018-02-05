package mumsched.service;

import mumsched.entity.Student;
import mumsched.entity.User;
import mumsched.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student findByUser(User user) {
        return studentRepository.findByUser(user);
    }
}
