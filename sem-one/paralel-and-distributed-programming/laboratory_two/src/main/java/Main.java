import model.*;

import java.util.ArrayDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var resource = Resource.initializeResource();
        System.out.printf("Vectors will have a size of %d%n", resource.getSize());
        var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var channel = new ArrayDeque<Message>();
        var producer = new Producer(channel, executorService, resource);
        var initialSum = 0L;
        var consumer = new Consumer(initialSum, channel, executorService);
        executorService.submit(() -> {
            producer.produceAsync();
            consumer.consume();
        });

        var terminated = executorService.awaitTermination(1, TimeUnit.SECONDS);
        while (!terminated){
            executorService.shutdown();
            terminated = executorService.awaitTermination(1, TimeUnit.SECONDS);
        }
        executorService.shutdownNow();
        System.out.printf("Cosnumer final sum is %d%n", consumer.getSum());
    }
}
