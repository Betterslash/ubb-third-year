package worker;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.matrix.MatrixElement;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class ThreadedMatrixWorker extends MatrixWorker{
    @Override
    public List<MatrixElement> invoke() throws InterruptedException {
        var result = new ArrayList<MatrixElement>();
        var threads = new ArrayList<Thread>();
        operationsBatches.forEach(e ->
            threads.add(new Thread(() ->{
                log.info("Created thread for task...");
                result.addAll(e.call());})));
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
            log.info("Finished thread for task...");
        }
        return result;
    }
}
