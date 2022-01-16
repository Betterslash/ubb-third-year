package service.lr_zero_parser;

import lombok.Getter;
import lombok.Setter;
import model.Grammar;
import service.Parser;

import java.util.List;

@Getter
@Setter
public class LRZeroParser extends Parser {
    protected LRZeroParser(Grammar grammar) {
        super(grammar);
    }

    @Override
    protected List<String> getDerivations() {
        return null;
    }
}
