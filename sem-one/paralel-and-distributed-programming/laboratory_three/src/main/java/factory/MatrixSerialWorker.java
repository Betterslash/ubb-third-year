package factory;

import model.MatrixOperation;

import java.util.List;

public class MatrixSerialWorker extends MatrixWorker{

    public MatrixSerialWorker(List<MatrixOperation> operationsToBeExecuted) {
        super(operationsToBeExecuted);
    }

    @Override
    void invoke() {
        operationsToBeExecuted.forEach(e -> e.execution().run());
    }
}
