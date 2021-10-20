package model.worker;

import lombok.extern.slf4j.Slf4j;
import model.MatrixElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPooledMatrixWorker extends MatrixWorker{
    @Override
    public List<MatrixElement> invoke() throws InterruptedException {
        var result = new ArrayList<MatrixElement>();
        operationsBatches
                .forEach(e -> e.forEach(p -> executorService.submit(() -> {
                    log.info("Created thread for task...");
                    result.add(p.call());
                    log.info("Finished operation ...");})));
        executorService.shutdown();
        var finisehd = executorService.awaitTermination(100, TimeUnit.SECONDS);
        executorService.shutdownNow();
        return result;
    }
}
