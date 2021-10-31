package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Product {
    public String productName;

    public int unitPrice;

    protected Product(String productNameParam, int unitPriceParam) {
        productName = productNameParam;
        unitPrice = unitPriceParam;
    }
}
