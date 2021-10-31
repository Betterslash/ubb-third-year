package util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString(includeFieldNames = false)
public abstract class HandsidePair<L, R>{
    public L leftHandside;
    public R rightHandside;
}
