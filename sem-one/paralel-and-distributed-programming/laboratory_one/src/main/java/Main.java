import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        var prop = new Properties();
        try {
            var mutex = new ReentrantLock();
            var sumsForCheck = new ArrayList<Integer>();
            var executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());;
            prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
            var customersNumber = Integer.parseInt(prop.getProperty("productsNumber"));
            var billNumber = Integer.parseInt(prop.getProperty("billNumber"));
            var billSize = Integer.parseInt(prop.getProperty("maxBillSize"));
            var store = new Store(customersNumber, executorService, mutex);
            var productsList =store.getProducts();
            var bills = new ArrayList<Bill>();
            for(int i = 0;i < billNumber; i++){
                bills.add(new Bill(productsList, billSize));
            }
            var consChecker = new ConsistencyChecker();
            var scheduler =
                    Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> consChecker.run(store, sumsForCheck, mutex), 100, 600, TimeUnit.MILLISECONDS);
            store.sell(bills, sumsForCheck);
            scheduler.awaitTermination((long) 1.9, TimeUnit.SECONDS);
            scheduler.shutdownNow();
            executorService.awaitTermination(2, TimeUnit.SECONDS);
            executorService.shutdownNow();
            consChecker.run(store, sumsForCheck, mutex);
            System.out.println(store.getSells());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
