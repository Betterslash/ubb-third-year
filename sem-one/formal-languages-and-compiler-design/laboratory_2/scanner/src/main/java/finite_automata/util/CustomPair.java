package finite_automata.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class CustomPair {
    private String state;
    private String route;
}
