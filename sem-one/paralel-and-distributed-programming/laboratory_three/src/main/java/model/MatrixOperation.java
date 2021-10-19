package model;

import lombok.Data;

@Data
public abstract class MatrixOperation {
    protected final int line;
    protected final int column;
    protected final Pair matrixes;
    public abstract Runnable execution();
}
