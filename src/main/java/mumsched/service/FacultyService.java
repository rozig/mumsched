package mumsched.service;

import mumsched.entity.Faculty;
import mumsched.entity.User;

public interface FacultyService {
    Faculty findByUser(User user);
}
