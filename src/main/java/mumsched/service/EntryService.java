package mumsched.service;

import mumsched.entity.Entry;

import java.util.List;

public interface EntryService {
    List<Entry> findAll();
    List<Entry> findAllExcept(Long id);
    Entry save(Entry entry);
    Entry findOne(Long id);
    void delete(Long id);
}
