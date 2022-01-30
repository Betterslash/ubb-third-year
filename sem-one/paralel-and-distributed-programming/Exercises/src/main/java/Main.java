import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException{


        var pcQueue = new ProducerConsumerQueue<Integer>();


        //[1 , 2]
        // push
        // pop
        // 5 / 3 = 1 r 2
        // 7 / 3 = 2 r 1
        for (int i = 0; i < 12105; i++) {
            var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            executorService.submit(pcQueue::dequeue);
            executorService.submit(pcQueue::dequeue);
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        }
        pcQueue.close();
        pcQueue.items.forEach(System.out::println);
        var lista = new ArrayList<Integer>();
        lista.addAll(Arrays.asList(1 , 2, 3,4 ,5 ,6));
        lista.remove(0);
        lista.forEach(System.out::println);
    }
}
