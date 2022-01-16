package service.lr_zero_parser;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class LRZeroStateProduction {
    private String leftHandside;
    private List<String> rightHandside;

    public LRZeroStateProduction copy(){
        return LRZeroStateProduction.builder()
                .leftHandside(leftHandside)
                .rightHandside(new ArrayList<>(rightHandside))
                .build();
    }

    public boolean isSame(LRZeroStateProduction production){
        if(!Objects.equals(production.leftHandside, this.leftHandside)){
            return false;
        }else{
            for (int i = 0; i < this.getRightHandside().size(); i++) {
                if (!Objects.equals(production.getRightHandside().get(i), this.getRightHandside().get(i))){
                    return false;
                }
            }
            return true;
        }
    }
}
