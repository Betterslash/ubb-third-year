package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import repository.ProductType;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
public class Customer {
    private final Bill bill;

    public Customer() {
        var billRepresentation = new HashMap<ProductType, Integer>();
        billRepresentation.put(ProductType.Phone, ThreadLocalRandom.current().nextInt(10));
        billRepresentation.put(ProductType.Accesory, ThreadLocalRandom.current().nextInt(20));
        billRepresentation.put(ProductType.Game, ThreadLocalRandom.current().nextInt(30));
        this.bill = new Bill(billRepresentation);
    }
}
