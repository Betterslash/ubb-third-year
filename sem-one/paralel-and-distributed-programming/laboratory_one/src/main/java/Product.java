import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
public class Product {
    private final UUID name;
    private final int unitPrice;

    public Product() {
        this.name = java.util.UUID.randomUUID();
        this.unitPrice = ThreadLocalRandom.current().nextInt(40);
    }

}
