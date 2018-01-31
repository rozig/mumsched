package mumsched.service;

import mumsched.entity.Entry;
import mumsched.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("entryService")
public class EntryServiceImpl implements EntryService {
    @Autowired
    EntryRepository entryRepository;

    @Override
    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    @Override
    public List<Entry> findAllExcept(Long id) {
        return entryRepository.findAllExcept(id);
    }

    @Override
    public Entry save(Entry entry) {
        return entryRepository.save(entry);
    }

    @Override
    public Entry findOne(Long id) {
        return entryRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        entryRepository.delete(id);
    }
}
