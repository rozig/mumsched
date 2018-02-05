package mumsched.service;

import mumsched.entity.Section;

import java.util.List;

public interface SectionService {
    List<Section> findAll();
    List<Section> findAllExcept(Long id);
    Section save(Section section);
    Section findOne(Long id);
    void delete(Long id);
}