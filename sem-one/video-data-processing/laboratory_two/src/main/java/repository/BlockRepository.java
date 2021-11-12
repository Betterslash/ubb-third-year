package repository;

import model.block.Block;

import java.util.List;

public interface BlockRepository{
    List<Block> getStorage();
    void printStorage();
}
