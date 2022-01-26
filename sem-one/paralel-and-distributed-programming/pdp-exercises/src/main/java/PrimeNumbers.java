import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PrimeNumbers {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static boolean isPrime(int number){
        if(number == 0 || number == 1){
            return false;
        }
        for (int i = 2; i < number / 2 + 1; i++) {
            if(number % 2 == 0){
                return false;
            }
        }
        return true;
    }

    public static List<Integer> filterBatch(List<Integer> batch){
        var result = batch.stream().filter(PrimeNumbers::isPrime).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getId() + " with result : " + result + " from batch : " + batch);
        if(result.size() == 0){
            return List.of();
        }
        return result;
    }

    public static List<Integer> execute(int N) throws InterruptedException {
        var numbers = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) {
            numbers.add(i);
        }
        var threadSize = N / Runtime.getRuntime().availableProcessors();
        var rest = N - (threadSize *  Runtime.getRuntime().availableProcessors());
        var tasks = new ArrayList<Thread>();
        var result = new ArrayList<Integer>();
        int i = 0;
        while (i != N){
            int finalI = i;
            if(rest > 0){
                tasks.add(new Thread(() -> result.addAll(filterBatch(numbers.subList(finalI, finalI + threadSize + 1)))));
                rest -= 1;
                i+= threadSize + 1;
            }else{
                tasks.add(new Thread(() -> result.addAll(filterBatch(numbers.subList(finalI, finalI + threadSize)))));
                i+= threadSize;
            }
        }
        tasks.forEach(executorService::submit);
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        return result;
    }
}
