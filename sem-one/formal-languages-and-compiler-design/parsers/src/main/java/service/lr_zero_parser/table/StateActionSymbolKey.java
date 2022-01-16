package service.lr_zero_parser.table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateActionSymbolKey {
    private final StateActionKey stateActionKey;
    private final String symbol;
}
