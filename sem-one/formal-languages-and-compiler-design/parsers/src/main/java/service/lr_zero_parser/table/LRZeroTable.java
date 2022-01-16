package service.lr_zero_parser.table;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import service.ProductionMapKey;
import service.lr_zero_parser.LRZeroState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class LRZeroTable {
    private final Set<LRZeroState> states;
    private final Map<ProductionMapKey, List<String>> mappedProductions;

    private final Map<StateActionSymbolKey, Integer> table = initializeTable();

    private Map<StateActionSymbolKey, Integer> initializeTable() {
        var result = new HashMap<StateActionSymbolKey, Integer>();
        states.forEach(e -> {
            var action = chooseAction(e);
            var symbol = chooseSymbol(e);
            var number =
        });
        return null;
    }

    private String chooseSymbol(LRZeroState e) {
    }

    private LRZeroAction chooseAction(LRZeroState e) {
    }


}
