package service.lr_zero_parser;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
@Builder
public class LRZeroState {

    private List<LRZeroStateProduction> lrZeroStateProduction;

    public LRZeroState copy(){
        return LRZeroState.builder()
                .lrZeroStateProduction(new ArrayList<>(lrZeroStateProduction))
                .build();
    }

    public boolean isSame(LRZeroState state){
        var res = new AtomicBoolean(true);
        this.lrZeroStateProduction.forEach(e -> state.lrZeroStateProduction.forEach(q -> {
            if(!q.isSame(e)){
                res.set(false);
            }
        }));
        return res.get();
    }

    public boolean notAlreadyIn(HashSet<LRZeroState> results) {
        var result = new AtomicBoolean(false);
        results.forEach(e -> {
            if(!this.isSame(e)){
                result.set(true);
            }
        });
        return result.get();
    }
}
