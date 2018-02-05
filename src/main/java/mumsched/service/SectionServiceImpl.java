package mumsched.service;

import mumsched.entity.Section;
import mumsched.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sectionService")
public class SectionServiceImpl implements SectionService {
    @Autowired
    SectionRepository sectionRepository;

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    @Override
    public List<Section> findAllExcept(Long id) {
        return sectionRepository.findAllExcept(id);
    }

    @Override
    public Section save(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public Section findOne(Long id) {
        return sectionRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        sectionRepository.delete(id);
    }
}
