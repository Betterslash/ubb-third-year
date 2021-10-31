import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Slf4j
public class Store {
    private Map<Product, Integer> stock;
    private List<Product> products;
    private final int productsNumber;
    private int maxProfit;
    private int currentProfit;

    private ExecutorService executorService;
    private List<Future<?>> threads;

    private final List<String> bills;

    public Store(int productsNumber, ExecutorService executorService) {
        this.productsNumber = productsNumber;
        this.bills = new ArrayList<>();
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
            var quantity = product.getQuantity();
            stock.put(product, quantity);
            products.add(product);
            maxProfit += product.getUnitPrice() * quantity;
        }
    }

    private void sellOne(Product product, int quantity){
        product.getMutex().lock();
        this.stock.entrySet()
                .stream()
                .filter(e -> e.getKey().getName() == product.getName())
                .findFirst()
                .ifPresent(e -> {
                    if(quantity <= e.getKey().getQuantity()) {
                        e.getKey().setQuantity(e.getValue() - quantity);
                        this.currentProfit += product.getUnitPrice() * quantity;
                    }
                });
        product.getMutex().unlock();
    }

    private Map.Entry<String, Integer> createMapEntry(String uuid, Integer quantity){
        return Map.entry(uuid, quantity);
    }

    private boolean checkBill(Sale sale){
        var result = new AtomicBoolean(true);
        var productsToBeSold = sale.getSaleRepresentation()
                .keySet()
                .stream()
                .map(Product::getName)
                .collect(Collectors.toList());
        var storeProducts = this.stock
                .keySet()
                .stream()
                .filter(integer -> productsToBeSold.contains(integer.getName()))
                .map(integer -> createMapEntry(integer.getName().toString(), integer.getQuantity()))
                .collect(Collectors.toList());
        sale.getSaleRepresentation()
                .forEach((key, value) -> storeProducts.forEach(q -> {
                    if (Objects.equals(q.getKey(), key.getName().toString())) {
                        if (value > q.getValue()) {
                            result.set(false);
                        }
                    }
                }));
        return result.get();
    }

    public void sell(List<Sale> sales, List<Integer> paidSums){
        sales.forEach(sale -> threads.add( executorService.submit(() ->{
            if (checkBill(sale)) {
                sale.getSaleRepresentation()
                        .forEach(this::sellOne);
                paidSums.add(sale.getTotalPrice());
                this.bills.add(sale.getSaleRepresentation().keySet().stream().toString());
                log.info(sale.toString());
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })));
    }


    public String getSells(){
        return "Store sold : " + this.currentProfit;
    }
}
