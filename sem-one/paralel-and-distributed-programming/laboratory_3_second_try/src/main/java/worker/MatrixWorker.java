package worker;

import model.matrix.MatrixElement;
import model.task.Task;
import model.task.TasksCreator;
import utils.RunningConfiguration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MatrixWorker {
    public final List<Task> operationsBatches;
    protected static ExecutorService executorService = Executors.newFixedThreadPool(RunningConfiguration.THREAD_NUMBER);

    protected MatrixWorker() {
        operationsBatches = TasksCreator.getTasks();
    }

    public abstract List<MatrixElement> invoke() throws InterruptedException;
}
