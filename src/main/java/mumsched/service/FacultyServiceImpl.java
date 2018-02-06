package mumsched.service;

import mumsched.entity.Faculty;
import mumsched.entity.User;
import mumsched.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("facultyService")
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyRepository facultyRepository;

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty findByUser(User user) {
        return facultyRepository.findByUser(user);
    }

    @Override
    public Faculty findOne(Long id) {
        return facultyRepository.findOne(id);
    }

    @Override
    public void save(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.delete(id);
    }
}
