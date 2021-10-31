package model.factories;

import lombok.Data;
import model.adts.PositionPair;
import model.workers.MatrixComputationsWorker;

import java.util.List;

@Data
public abstract class MatrixWorkerFactory {
    protected final List<PositionPair> positions;

    public abstract MatrixComputationsWorker createWorker();
}
