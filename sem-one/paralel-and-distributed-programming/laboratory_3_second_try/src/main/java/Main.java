import computations.MatrixElementComputation;
import exception.RunningConfigurationException;
import utils.ExecutionType;
import utils.RunningConfiguration;
import factory.WorkerFactory;
import org.apache.commons.lang3.time.StopWatch;
import utils.WorkerType;

import java.io.IOException;

public class Main {
    private static void executeOne() throws InterruptedException {
        var worker = WorkerFactory.createWorker();
        var watch = new StopWatch();
        watch.start();
        worker.invoke();
        watch.stop();
        var message = String.format("Time Elapsed: %dms \n -> executing computations in a %s manner  \n -> with partition strategy %s",
                watch.getTime(),
                RunningConfiguration.WORKER_TYPE.name(),
                RunningConfiguration.SPLITTING_TYPE.name());
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RunningConfiguration.initializeConfigurations();
        MatrixElementComputation.initializeMatrixes();

        if(RunningConfiguration.EXECUTION_TYPE == ExecutionType.ALL){
            executeAll();
        }else if(RunningConfiguration.EXECUTION_TYPE == ExecutionType.ONE){
            executeOne();
        }else {
            throw new RunningConfigurationException("EXECUTION_TYPE must be provided in the configuration!!");
        }

    }
    private static void executeAll() throws InterruptedException {
        var watch = new StopWatch();

        RunningConfiguration.WORKER_TYPE = WorkerType.SERIAL;
        watchWorker(watch);

        RunningConfiguration.WORKER_TYPE = WorkerType.THREADED;
        watchWorker(watch);

        RunningConfiguration.WORKER_TYPE = WorkerType.THREAD_POOLED;
        watchWorker(watch);
    }

    private static void watchWorker(StopWatch watch) throws InterruptedException {
        String message;
        watch.reset();
        watch.start();
        var threadedWorker = WorkerFactory.createWorker();
        threadedWorker.invoke();
        watch.stop();
        message = String.format("Time Elapsed: %dms \n -> executing computations in a %s manner  \n -> with partition strategy %s",
                watch.getTime(),
                RunningConfiguration.WORKER_TYPE.name(),
                RunningConfiguration.SPLITTING_TYPE.name());
        System.out.println(message);
    }
}
