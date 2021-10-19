package factory;

import lombok.RequiredArgsConstructor;
import model.MatrixOperation;

import java.util.List;

@RequiredArgsConstructor
public abstract class MatrixWorker {
    protected final List<MatrixOperation> operationsToBeExecuted;

    abstract void invoke();
}
