package model.workers;

import lombok.Data;
import model.MatrixElement;
import model.operations.MatrixComputation;

import java.util.List;

@Data
public abstract class MatrixComputationsWorker {
    protected final List<MatrixComputation> operations;

    public abstract List<MatrixElement> executeOperations();
}
