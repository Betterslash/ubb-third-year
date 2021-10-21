package worker;

import model.matrix.MatrixElement;

import java.util.ArrayList;
import java.util.List;

public class SerialMatrixWorker extends MatrixWorker {
    @Override
    public List<MatrixElement> invoke() {
        var result = new ArrayList<MatrixElement>();
        operationsBatches.forEach(e -> result.addAll(e.call()));
        return result;
    }
}
