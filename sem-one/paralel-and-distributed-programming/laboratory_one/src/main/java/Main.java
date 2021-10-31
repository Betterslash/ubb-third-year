import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try {
            ProgramInitializer.initialize();
            var sumsForCheck = new ArrayList<Integer>();
            var bills = new ArrayList<Sale>();
            var consChecker = new ConsistencyChecker();
            var executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
                    .availableProcessors());

            var store = new Store(ProgramInitializer.PRODUCTS_NUMBER, executorService);
            var scheduler =
                    Executors.newScheduledThreadPool(1);
            scheduler.scheduleWithFixedDelay(() ->
                            consChecker.run(store, sumsForCheck),
                    0, 65, TimeUnit.MILLISECONDS);

            var productsList =store.getProducts();
            for(int i = 0;i < ProgramInitializer.SELL_NUMBER; i++){
                bills.add(new Sale(productsList, ProgramInitializer.SELL_SIZE));
            }


            store.sell(bills, sumsForCheck);
            executorService.shutdown();
            var isFinished = executorService.awaitTermination(1, TimeUnit.SECONDS);
            if(isFinished) {
                scheduler.shutdownNow();
                executorService.shutdownNow();
            }
            consChecker.run(store, sumsForCheck);
            System.out.println(store.getSells());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
