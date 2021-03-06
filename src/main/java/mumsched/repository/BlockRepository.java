package mumsched.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mumsched.entity.Block;
import mumsched.entity.Entry;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT c FROM Block c WHERE c.id <> :id")
    public List<Block> findAllExcept(@Param("id") Long id);
    
    @Query(value = "select * from block b INNER JOIN entry_blocks eb ON b.id = eb.block_id "
    		+ "INNER JOIN entry e ON eb.entry_id = e.id WHERE e.id=:id order by b.start_date",nativeQuery=true)
	public List<Block> findByEntryId(@Param("id") Long id);
    
    @Query(value = "select * from block b WHERE b.start_date < (select bat.start_date from block bat WHERE bat.id = :id )",nativeQuery=true)
    List<Block> findByStartDateBefore(@Param("id") Long block_id);
}
