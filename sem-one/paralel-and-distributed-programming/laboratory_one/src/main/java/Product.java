import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
@ToString
public class Product {
    private final UUID name;
    private final int unitPrice;
    private final ReentrantLock mutex;
    private int quantity;
    public Product() {
        this.name = java.util.UUID.randomUUID();
        this.unitPrice = ThreadLocalRandom.current().nextInt(40);
        this.quantity = ThreadLocalRandom.current().nextInt(50);
        this.mutex = new ReentrantLock();
    }

}
