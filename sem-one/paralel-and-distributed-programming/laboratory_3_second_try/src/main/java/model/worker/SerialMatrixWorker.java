package model.worker;

import model.MatrixElement;

import java.util.ArrayList;
import java.util.List;

public class SerialMatrixWorker extends MatrixWorker {
    @Override
    public List<MatrixElement> invoke() {
        var result = new ArrayList<MatrixElement>();
        operationsBatches.forEach(e -> e.forEach(q -> {
            try {
                result.add(q.call());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));
        return result;
    }
}
