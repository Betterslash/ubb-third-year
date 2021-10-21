package model.matrix;

import lombok.Data;

@Data
public class MatrixElement {
    private final int value;
    private final int line;
    private final int column;
}
