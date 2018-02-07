package mumsched.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mumsched.entity.Block;
import mumsched.repository.BlockRepository;

@Service("blockService")
public class BlockServiceImpl implements BlockService {
    @Autowired
    BlockRepository blockRepository;

    @Override
    public List<Block> findAll() {
        return blockRepository.findAll();
    }

    @Override
    public List<Block> findAllExcept(Long id) {
        return blockRepository.findAllExcept(id);
    }

    @Override
    public Block save(Block block) {
        return blockRepository.save(block);
    }

    @Override
    public Block findOne(Long id) {
        return blockRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        blockRepository.delete(id);
    }
}
