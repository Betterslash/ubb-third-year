package model.block;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Block {
    private final double[][] representation;
}
