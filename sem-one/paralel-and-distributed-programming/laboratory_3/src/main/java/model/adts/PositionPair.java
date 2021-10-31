package model.adts;

import lombok.Data;

@Data
public class PositionPair {
    private final Position start;
    private final Position end;
}
