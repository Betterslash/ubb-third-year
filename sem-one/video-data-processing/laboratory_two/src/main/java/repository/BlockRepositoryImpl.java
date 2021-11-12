package repository;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import model.block.Block;

import java.util.List;

@Data
@Builder
@Getter
public class BlockRepositoryImpl implements BlockRepository {
    private final List<Block> storage;

    @Override
    public void printStorage() {
        AbstractBlockRepository.printStorage(this.storage);
    }
}
