import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ConsistencyChecker {
    public void run(Store store, List<Integer> sumsForCheck){
        store.getProducts().forEach(e -> e.getMutex().lock());
        var currentStoreProfit = store.getCurrentProfit();
        sumsForCheck.stream()
                .reduce(Integer::sum)
                .ifPresent(e -> {
                    var result = (e == currentStoreProfit);
                    var message = String.format("Ran consistency check : Result -> %s", result);
                    store.getBills().add(message);
                    log.info(message);
                });
        store.getProducts().forEach(e -> e.getMutex().unlock());
    }
}
