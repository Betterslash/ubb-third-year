package repository;

import lombok.Getter;
import lombok.Setter;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class Store {
    private final Map<ProductType, Integer> stock;
    private int money;
    private List<Bill> bills;
    private ExecutorService executorService;
    private List<Future<?>> threads;
    private static ReentrantLock mutex = new ReentrantLock();
    private int maxSum;

    public Store() {
        this.threads = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.money = 0;
        this.bills = new ArrayList<>();
        this.stock = new HashMap<>();
        this.stock.put(ProductType.Phone, 10);
        this.stock.put(ProductType.Accesory, 80);
        this.stock.put(ProductType.Game, 60);
        computeMaxSum();
    }

    private void computeMaxSum(){
        this.maxSum = 0;
        this.maxSum += new Phone().unitPrice * this.stock.get(ProductType.Phone);
        this.maxSum += new Game().unitPrice * this.stock.get(ProductType.Game);
        this.maxSum += new Accesory().unitPrice * this.stock.get(ProductType.Accesory);
    }

    private void sellOne(int quantity, ProductType productType){
        mutex.lock();
        var onStock = this.stock.get(productType);
        if(onStock > 0 && onStock >= quantity){
            switch (productType){
                case Phone -> this.money += new Phone().unitPrice * quantity;
                case Game -> this.money += new Game().unitPrice * quantity;
                case Accesory -> this.money += new Accesory().unitPrice * quantity;
            }
            onStock -= quantity;
            this.stock.put(productType, onStock);
        }
        mutex.unlock();
    }

    public void sell(Bill bill){

        bill.getBillRepresentation()
                .forEach((key, value) -> {
                    threads.add(executorService.submit(() -> this.sellOne(value, key)));
                });
        this.bills.add(bill);

    }

    public void startSelling() throws InterruptedException, ExecutionException {
        for (Future<?> thread : this.threads) {
            thread.get();
        }
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdownNow();
    }
}
