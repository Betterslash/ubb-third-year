package service.descendent_recursive_parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductionMapKey {
    private int productionNumber;
    private String productionLeftHandside;

    public ProductionMapKey copy() {
        return ProductionMapKey.builder()
                .productionLeftHandside(productionLeftHandside)
                .productionNumber(productionNumber)
                .build();
    }
}
