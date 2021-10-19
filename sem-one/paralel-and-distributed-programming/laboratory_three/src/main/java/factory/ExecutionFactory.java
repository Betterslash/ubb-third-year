package factory;

public interface ExecutionFactory {
    MatrixWorker createWorker(WorkerConfiguration workerConfiguration);
}
