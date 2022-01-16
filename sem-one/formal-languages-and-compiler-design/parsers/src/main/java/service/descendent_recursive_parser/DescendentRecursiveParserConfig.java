package service.descendent_recursive_parser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Stack;

@Getter
@Setter
@Builder
public class DescendentRecursiveParserConfig{
    private DescendentRecursiveParserState state;

    private int position;

    private Stack<String> workingStack;

    private Stack<String> inputStack;

    public static DescendentRecursiveParserConfig getInitialConfigurationForSequence(String startSymbol){
        var startStack = new Stack<String>();
        startStack.push(startSymbol);
        return DescendentRecursiveParserConfig.builder()
                .state(DescendentRecursiveParserState.Q)
                .position(1)
                .workingStack(new Stack<>())
                .inputStack(startStack)
                .build();
    }

    public DescendentRecursiveParserConfig copy() {
        var state = this.getState();
        var position = this.getPosition();
        var workingStack = new Stack<String>();
        this.getWorkingStack().forEach(workingStack::push);
        var inputStack = new Stack<String>();
        this.getInputStack().forEach(inputStack::push);
        return DescendentRecursiveParserConfig.builder()
                .state(state)
                .position(position)
                .inputStack(inputStack)
                .workingStack(workingStack)
                .build();
    }
}
