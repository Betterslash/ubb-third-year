package finite_automata.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class HandSideAutomataPair extends HandsidePair<CustomPair, String>{
    public String toFileFormat(){
        return String.format("\t(%s, %s) -> %s\n", this.leftHandside.getState(), this.leftHandside.getRoute(), this.rightHandside);
    }
}
