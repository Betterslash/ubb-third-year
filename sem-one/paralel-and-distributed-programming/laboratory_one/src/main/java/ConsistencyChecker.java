import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ConsistencyChecker {
    public void run(Store store, List<Integer> sumsForCheck, ReentrantLock lock){
        lock.lock();
        var currentStoreProfit = store.getCurrentProfit();
        sumsForCheck.stream()
                .reduce(Integer::sum)
                .ifPresent(e -> {
                    System.out.printf("Bills paid have value : %d%n",e);
                    System.out.printf("Store has a current profit of : %d%n", currentStoreProfit);
                });
        lock.unlock();
    }
}
