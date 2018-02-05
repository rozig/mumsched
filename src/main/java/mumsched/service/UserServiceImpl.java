package mumsched.service;

import mumsched.entity.Profile;
import mumsched.entity.Student;
import mumsched.entity.User;
import mumsched.entity.UserRoles;
import mumsched.repository.FacultyRepository;
import mumsched.repository.StudentRepository;
import mumsched.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByActivationToken(String token) {
        return userRepository.findByActivationToken(token);
    }

    @Override
    public void saveNewStudentUserWithProfile(User user, Student student) {
        save(user);
        student.setUser(user);
        studentRepository.save(student);
    }

    public Profile getUserProfile(User user) {
        if (user.getRoles().contains(UserRoles.STUDENT.getValue())) {
            return studentRepository.findByUser(user);
        } else if (user.getRoles().contains(UserRoles.FACULTY.getValue())) {
            return facultyRepository.findByUser(user);
        }

        return null;
    }
}
