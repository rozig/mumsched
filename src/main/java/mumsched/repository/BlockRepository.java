package mumsched.repository;

import mumsched.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT c FROM Block c WHERE c.id <> :id")
    public List<Block> findAllExcept(@Param("id") Long id);
    
    @Query(value = "select * from block a,entry_blocks b where a.id = b.block_id and b.entry_id=?1 order by b.start_date", 
			nativeQuery = true)
	public List<Block> findBlocksByEntryId(Long entryId);
}
