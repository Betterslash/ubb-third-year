import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
public class Sale {

    private final Map<Product, Integer> saleRepresentation;
    private int totalPrice;

    public Sale(List<Product> products, int maxBillSize) {
        totalPrice = 0;
        this.saleRepresentation = new HashMap<>();
        var positions = new HashSet<Integer>();
        var random = new Random();
        var size = random.nextInt(maxBillSize);
        while (size < 1){
            size = random.nextInt(maxBillSize);
        }
        while(positions.size() < size){
            var position = random.nextInt(products.size() - 1);
            positions.add(position);
        }
        positions.forEach(e -> saleRepresentation.put(products.get(e), ThreadLocalRandom
                .current()
                .nextInt(10)));
        saleRepresentation.forEach((key, value) -> totalPrice += value * key.getUnitPrice());
    }
}
