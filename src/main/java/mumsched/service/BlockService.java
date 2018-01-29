package mumsched.service;

import mumsched.entity.Block;

import java.util.List;

public interface BlockService {
    List<Block> findAll();
    List<Block> findAllExcept(Long id);
    Block save(Block block);
    Block findOne(Long id);
    void delete(Long id);
}
