import model.computations.MatrixElementComputation;
import model.RunningConfiguration;
import model.factory.WorkerFactory;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        RunningConfiguration.initializeConfigurations();
        MatrixElementComputation.initializeMatrixes();
        var worker = WorkerFactory.createWorker();
        var watch = new StopWatch();
        watch.start();
        var results = worker.invoke();
        watch.stop();
        System.out.println("Time Elapsed: " + watch.getTime());
    }
}
