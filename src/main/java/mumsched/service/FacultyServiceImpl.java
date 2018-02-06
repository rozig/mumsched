package mumsched.service;

import mumsched.entity.Faculty;
import mumsched.entity.User;
import mumsched.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("facultyService")
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyRepository facultyRepository;

    @Override
    public Faculty findByUser(User user) {
        return facultyRepository.findByUser(user);
    }

    @Override
    public void save(Faculty faculty) {
        facultyRepository.save(faculty);
    }
}
