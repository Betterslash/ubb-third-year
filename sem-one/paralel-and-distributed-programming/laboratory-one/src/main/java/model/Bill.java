package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import repository.ProductType;

import java.util.Map;

@Getter
@Setter
@ToString
public class Bill {
    private final Map<ProductType, Integer> billRepresentation;

    public Bill(Map<ProductType, Integer> billRepresentation) {
        this.billRepresentation = billRepresentation;
    }
}
