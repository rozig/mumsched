package mumsched.service;

import mumsched.entity.Student;
import mumsched.entity.Faculty;
import mumsched.entity.User;

public interface UserService {
    public User findByEmail(String email);

    public void save(User user);

    public User findByActivationToken(String token);

    public void saveNewStudentUserWithProfile(User user, Student student);

    public void saveNewFacultyUserWithProfile(User user, Faculty faculty);

    void delete(Long id);
}
