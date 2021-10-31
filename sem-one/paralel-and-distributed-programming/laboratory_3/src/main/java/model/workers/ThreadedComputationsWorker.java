package model.workers;

import model.MatrixElement;
import model.operations.MatrixComputation;

import java.util.List;

public class ThreadedComputationsWorker extends MatrixComputationsWorker{


    public ThreadedComputationsWorker(List<MatrixComputation> operations) {
        super(operations);
    }

    @Override
    public List<MatrixElement> executeOperations() {
        return null;
    }
}
