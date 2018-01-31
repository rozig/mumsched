package mumsched.repository;

import mumsched.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    @Query("SELECT c FROM Entry c WHERE c.id <> :id")
    public List<Entry> findAllExcept(@Param("id") Long id);
}
