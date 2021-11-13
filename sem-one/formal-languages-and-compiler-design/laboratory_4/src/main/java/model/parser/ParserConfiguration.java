package model.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import model.ParserSymbol;
import utils.enums.ParserStates;

import java.util.Stack;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ParserConfiguration {
    private ParserStates state = ParserStates.Q;
    private Integer positionOfCurrentSymbolInSequence = 0;
    private final Stack<ParserSymbol> workingStack = new Stack<>();
    private final Stack<ParserSymbol> inputStack = new Stack<>();
}
