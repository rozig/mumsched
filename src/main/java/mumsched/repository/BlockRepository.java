package mumsched.repository;

import mumsched.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT c FROM Block c WHERE c.id <> :id")
    public List<Block> findAllExcept(@Param("id") Long id);
    
    List<Block> findByStartDateAfter(Long id);
    List<Block> findByStartDateBefore(Long id);
}
