package model.worker;

import model.MatrixElement;
import model.RunningConfiguration;
import model.TasksCreator;
import model.computations.MatrixElementComputation;
import model.factory.MatrixComputationFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public abstract class MatrixWorker {
    public final List<List<? extends MatrixElementComputation>> operationsBatches;
    protected static ExecutorService executorService = Executors.newFixedThreadPool(RunningConfiguration.THREAD_NUMBER);

    protected MatrixWorker() {
        operationsBatches = TasksCreator.getTasks().stream()
                .map(e -> e.stream().map(MatrixComputationFactory::createComputation).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public abstract List<MatrixElement> invoke() throws InterruptedException;
}
