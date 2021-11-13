package model.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import model.ParserSymbol;
import model.grammar.Grammar;
import utils.enums.ParserStates;
import utils.enums.PossibleMoves;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Setter
@Getter
@RequiredArgsConstructor
public class DescendentRecursiveParser implements Parser {

    private final Grammar grammar = new Grammar();
    private final ParserConfiguration internalConfiguration = new ParserConfiguration();
    private final List<PossibleMoves> possibleMoves = Arrays.asList(PossibleMoves.values());
    private  String currentSequence = "";

    private String getSequence(){
        var scanner = new Scanner(System.in);
        System.out.println("Print your sequence here master >>");
        return scanner.next();
    }

    private boolean isInputStackHeadTerminal(){
        var symbol = getHeadOfTheInputStack()
                .getSymbol()
                .charAt(0);
        return grammar.isInTerminals(symbol);
    }
    private boolean isWorkingStackHeadTerminal(){
        var symbol = this.internalConfiguration.getWorkingStack().lastElement()
                .getSymbol()
                .charAt(0);
        return grammar.isInTerminals(symbol);
    }

    private void fromInputToWrokingStack(){
        this.internalConfiguration.getWorkingStack()
                .push(this.internalConfiguration
                        .getInputStack()
                        .pop());
    }

    private void expand(){
        if(!isInputStackHeadTerminal()){
            var head = getHeadOfTheInputStack();
            fromInputToWrokingStack();
            grammar.getP()
                    .stream().filter(e -> e.getLeftHandside().charAt(0) == head.getSymbol().charAt(0))
                    .findFirst()
                    .ifPresent(e -> this.internalConfiguration.getInputStack().push(
                            ParserSymbol.builder()
                                    .symbol(e.getRightHandside().get(head.getPosition()))
                                    .position(1)
                                    .build()));
        }
    }

    private void advance(){
        if(isInputStackHeadTerminal()){
            fromInputToWrokingStack();
            var position = this.internalConfiguration.getPositionOfCurrentSymbolInSequence() + 1;
            this.internalConfiguration.setPositionOfCurrentSymbolInSequence(position);
        }
    }

    private ParserSymbol getHeadOfTheInputStack(){
        return this.internalConfiguration
                .getInputStack()
                .lastElement();
    }

    private void momentaryInsuccess(){
        var head = getHeadOfTheInputStack()
                .getSymbol()
                .charAt(0);
        if(isInputStackHeadTerminal() && head != currentSequence.charAt(this.internalConfiguration.getPositionOfCurrentSymbolInSequence())){
            this.internalConfiguration.setState(ParserStates.B);
        }
    }

    private void back(){
        if(isInputStackHeadTerminal()){
            var lastElement = this.internalConfiguration
                    .getWorkingStack()
                    .pop();
            this.internalConfiguration.getInputStack().push(lastElement);
            var position = this.internalConfiguration.getPositionOfCurrentSymbolInSequence() - 1;
            this.internalConfiguration.setPositionOfCurrentSymbolInSequence(position);
        }
    }

    private void anotherTry(){
        if(!isInputStackHeadTerminal()){
            var head = getHeadOfTheInputStack();
            this.grammar.getP()
                    .stream()
                    .filter(e -> e.getLeftHandside().charAt(0) == head.getSymbol().charAt(0))
                    .findFirst()
                    .ifPresentOrElse(e -> {
                                try{
                                    if(e.getRightHandside().size() > head.getPosition()){
                                        this.internalConfiguration.setState(ParserStates.Q);
                                        this.internalConfiguration.getWorkingStack().forEach(q -> {if(Objects.equals(q.getSymbol(), head.getSymbol())){
                                            q.setPosition(q.getPosition() + 1);
                                            this.internalConfiguration.getInputStack().lastElement().setPosition(q.getPosition() + 1);
                                        }});
                                    }else{
                                        this.internalConfiguration.setState(ParserStates.B);
                                        this.internalConfiguration.getInputStack().push(this.internalConfiguration.getWorkingStack().lastElement());
                                    }
                                }catch (RuntimeException runtimeException){
                                    if(this.internalConfiguration.getPositionOfCurrentSymbolInSequence() == 1){
                                        this.internalConfiguration.setState(ParserStates.E);
                                    }
                                }
                            },
                            () -> {});
        }
    }

    private void success(){
        this.internalConfiguration.setState(ParserStates.F);
    }

    @Override
    public boolean parse() {
        this.currentSequence = getSequence();
        var configState = this.internalConfiguration.getState();
        while (configState != ParserStates.S && configState != ParserStates.E){
            if(configState == ParserStates.Q){
                if(this.internalConfiguration.getPositionOfCurrentSymbolInSequence()==this.currentSequence.length()
                && this.internalConfiguration.getInputStack().size() > 0) {
                    success();
                }else {
                    if(!isInputStackHeadTerminal()){
                        expand();
                    }else if(isInputStackHeadTerminal()){
                        advance();
                    }else {
                        momentaryInsuccess();
                    }
                }
            }else if(configState == ParserStates.B){
                if(isWorkingStackHeadTerminal()){
                    back();
                }else{
                    anotherTry();
                }
            }
            configState = this.internalConfiguration.getState();
        }
        return configState != ParserStates.E;
    }
}
