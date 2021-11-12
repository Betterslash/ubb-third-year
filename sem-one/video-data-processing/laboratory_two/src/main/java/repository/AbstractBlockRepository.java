package repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.block.Block;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public abstract class AbstractBlockRepository implements BlockRepository{
    protected List<Block> storage;
    public abstract void addToStorage(Block block);
    public void printStorage() {
        printStorage(this.storage);
    }

    static void printStorage(List<Block> storage) {
        storage
                .stream()
                .map(Block::getRepresentation)
                .map(Arrays::stream)
                .forEach(e -> {
                    e.map(Arrays::stream)
                            .forEach(t -> t.forEach(q -> System.out.print(q + " ")));
                    System.out.println(System.lineSeparator());
                });
    }
}
