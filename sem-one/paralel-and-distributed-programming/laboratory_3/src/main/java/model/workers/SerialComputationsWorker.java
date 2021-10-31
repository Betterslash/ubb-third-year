package model.workers;

import model.MatrixElement;
import model.operations.MatrixComputation;

import java.util.ArrayList;
import java.util.List;

public class SerialComputationsWorker extends MatrixComputationsWorker{

    public SerialComputationsWorker(List<MatrixComputation> operations) {
        super(operations);
    }

    @Override
    public List<MatrixElement> executeOperations() {
        var result = new ArrayList<MatrixElement>();
        operations.forEach(e -> {
            try {
                result.add(e.computeElement().call());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return result;
    }
}
