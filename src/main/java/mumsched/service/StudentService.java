package mumsched.service;

import mumsched.entity.Block;
import mumsched.entity.Student;
import mumsched.entity.User;

import java.util.List;

public interface StudentService {
    Student findByUser(User user);
}
