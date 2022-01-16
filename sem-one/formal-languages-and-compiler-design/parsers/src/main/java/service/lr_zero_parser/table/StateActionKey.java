package service.lr_zero_parser.table;

import lombok.Builder;
import lombok.Data;
import service.lr_zero_parser.LRZeroState;

@Data
@Builder
public class StateActionKey {
    private final LRZeroAction action;
    private final LRZeroState state;
}
