import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
public class Bill {

    private final Map<Product, Integer> billRepresentation;
    private int totalPrice;

    public Bill(List<Product> products, int maxBillSize) {
        totalPrice = 0;
        this.billRepresentation = new HashMap<>();
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
        positions.forEach(e -> billRepresentation.put(products.get(e), ThreadLocalRandom
                .current()
                .nextInt(10)));
        billRepresentation.forEach((key, value) -> totalPrice += value * key.getUnitPrice());
    }

    public String simpleFormat(){
        return "Bill with price : " + this.totalPrice;
    }
}
