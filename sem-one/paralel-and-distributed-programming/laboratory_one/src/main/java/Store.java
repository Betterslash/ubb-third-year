import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class Store {
    private Map<Product, Integer> stock;
    private List<Product> products;
    private final int productsNumber;
    private int maxProfit;
    private int currentProfit;
    private ExecutorService executorService;
    private List<Future<?>> threads;
    private final ReentrantLock mutex;

    public Store(int productsNumber, ExecutorService executorService, ReentrantLock mutex) {
        this.productsNumber = productsNumber;
        this.mutex = mutex;
        this.currentProfit = 0;
        this.executorService = executorService;
        stock = new HashMap<>();
        products = new ArrayList<>();
        threads = new ArrayList<>();
        initializeStore();
    }

    private void initializeStore(){
        for(int i = 0; i < productsNumber; i++){
            var product = new Product();
            var quantity = ThreadLocalRandom.current().nextInt(130);
            stock.put(product, quantity);
            products.add(product);
            maxProfit += product.getUnitPrice() * quantity;
        }
    }

    private void sellOne(Product product, int quantity){
        mutex.lock();
        this.stock.entrySet()
                .stream()
                .filter(e -> e.getKey().getName() == product.getName())
                .findFirst()
                .ifPresent(e -> {
                    if(quantity <= e.getValue()) {
                        e.setValue(e.getValue() - quantity);
                    }
                });
        this.currentProfit += product.getUnitPrice() * quantity;
        mutex.unlock();
    }

    private Map.Entry<String, Integer> createMapEntry(String uuid, Integer quantity){
        return Map.entry(uuid, quantity);
    }

    private boolean checkBill(Bill bill){
        var result = new AtomicBoolean(true);
        var productsToBeSold = bill.getBillRepresentation()
                .keySet()
                .stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        var storeProducts = this.stock
                .entrySet()
                .stream()
                .filter(e -> productsToBeSold.contains(e.getKey().getName()))
                .map(e -> createMapEntry(e.getKey().getName().toString(), e.getValue()))
                .collect(Collectors.toList());
        bill.getBillRepresentation()
                .forEach((key, value) -> storeProducts.forEach(q -> {
                    if (Objects.equals(q.getKey(), key.getName().toString())) {
                        if (value > q.getValue()) {
                            result.set(false);
                        }
                    }
                }));
        return result.get();
    }

    public void sell(List<Bill> bills, List<Integer> paidSums){
        bills.forEach(bill -> threads.add( executorService.submit(() ->{
            if (checkBill(bill)) {
                bill.getBillRepresentation()
                        .forEach(this::sellOne);
                System.out.println(bill.simpleFormat());
                paidSums.add(bill.getTotalPrice());
            }
            try {
                Thread.sleep(550);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })));
    }


    public String getSells(){
        return "Store sold : " + this.currentProfit;
    }
}
