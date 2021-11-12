package repository;

import lombok.EqualsAndHashCode;
import model.block.Block;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class GBlockRepository extends AbstractBlockRepository {

    public GBlockRepository(List<Block> storage) {
        super(storage);
    }

    @Override
    public void addToStorage(Block block) {
        this.storage.add(block);
    }
}
