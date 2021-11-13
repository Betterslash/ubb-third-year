package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParserSymbol {
    private String symbol;
    private Integer position;
}
