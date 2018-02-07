package mumsched.service;

import mumsched.entity.Faculty;
import mumsched.entity.User;

import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();
    Faculty findByUser(User user);
    Faculty findOne(Long id);
    public void save(Faculty faculty);
    public void delete(Long id);
}
