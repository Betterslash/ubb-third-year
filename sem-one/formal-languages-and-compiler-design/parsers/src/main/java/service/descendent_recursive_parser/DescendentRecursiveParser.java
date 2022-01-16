package service.descendent_recursive_parser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.Grammar;
import service.Parser;
import service.ProductionMapKey;

import java.util.*;

@Getter
@Setter
@ToString
public class DescendentRecursiveParser extends Parser {

    private final Map<ProductionMapKey, List<String>> mappedGrammarProductions;
    private DescendentRecursiveParserConfig configuration;
    private DescendentRecursiveParserConfig beforeConfiguration;
    private ProductionMapKey beforeProductionMapKey;

    public DescendentRecursiveParser(Grammar grammar){
        super(grammar);
        configuration = DescendentRecursiveParserConfig.getInitialConfigurationForSequence(grammar.getS());
        mappedGrammarProductions = getMappedProductions();
    }

    private DescendentRecursiveParserConfig back(){
        var config = this.configuration;
        var head = configuration.getWorkingStack().remove(0);
        config.getInputStack().push(head);
        config.setPosition(config.getPosition() - 1);
        return config;
    }

    private DescendentRecursiveParserConfig expand(){
        var config = this.configuration;
        this.beforeConfiguration = this.configuration.copy();
        var head = config.getInputStack().remove(0);
        var entry = this.mappedGrammarProductions
                .entrySet()
                .stream()
                .filter(e -> e.getKey().getProductionNumber() == 1 && Objects.equals(e.getKey().getProductionLeftHandside(), head))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        this.beforeProductionMapKey = entry.getKey().copy();
        var production = new ArrayList<>(entry.getValue());
        var index = 0;
        for (var p:production) {
            config.getInputStack().add(index, p);
            index++;
        }
        config.getWorkingStack().push(head+ "1");
        return config;
    }

    private DescendentRecursiveParserConfig momentaryInsuccess(){
        var config = this.configuration;
        config.setState(DescendentRecursiveParserState.B);
        return config;
    }

    private DescendentRecursiveParserConfig advance(){
        var config = this.configuration;
        var head = config.getInputStack().remove(0);
        config.getWorkingStack().push(head);
        var position = config.getPosition();
        config.setPosition(position + 1);
        return config;
    }

    private DescendentRecursiveParserConfig anotherTry(){
        var config = this.beforeConfiguration.copy();
        var key = this.beforeProductionMapKey.copy();
        var nextProductionNumber = key.getProductionNumber() + 1;
        var nextProduction = this.mappedGrammarProductions
                .entrySet()
                .stream()
                .filter(e -> e.getKey().getProductionNumber() == nextProductionNumber && Objects.equals(e.getKey().getProductionLeftHandside(), key.getProductionLeftHandside()))
                .findFirst()
                .orElse(null);
        if(nextProduction != null){
            var production = new ArrayList<>(nextProduction.getValue());
            config.getInputStack().remove(0);
            production.forEach(e -> config.getInputStack().push(e));
            config.setState(DescendentRecursiveParserState.Q);
            config.getWorkingStack().push(nextProduction.getKey().getProductionLeftHandside() + nextProduction.getKey().getProductionNumber());
            this.beforeProductionMapKey.setProductionNumber(this.beforeProductionMapKey.getProductionNumber() + 1);
            return config;
        }else if(config.getPosition() != 1 || !Objects.equals(this.configuration.getWorkingStack().firstElement(), grammar.getS())){
            var res = this.beforeConfiguration.copy();
            res.setState(DescendentRecursiveParserState.B);
            return res;
        }else{
            var res = this.beforeConfiguration.copy();
            res.setState(DescendentRecursiveParserState.E);
            return res;
        }
    }

    private DescendentRecursiveParserConfig success(){
        var config = this.configuration;
        config.setState(DescendentRecursiveParserState.F);
        return config;
    }

    private Map<ProductionMapKey, List<String>> getMappedProductions(){
        var productions = grammar.getP();
        var result = new HashMap<ProductionMapKey, List<String>>();
        for (var production: productions) {
            var productionRhs = production.getRightHandside();
            var index = 1;
            for (var prodRhs: productionRhs) {
                var key = ProductionMapKey.builder()
                        .productionLeftHandside(production.getLeftHandside())
                        .productionNumber(index)
                        .build();
                result.put(key, prodRhs);
                index += 1;
            }
        }
        return result;
    }

    public DescendentRecursiveParserConfig parse(List<String> sequence){
        var config = this.configuration;
        var state = config.getState();
        var pos = config.getPosition();
        var headInput = config.getInputStack().firstElement();
        while(state != DescendentRecursiveParserState.F && state != DescendentRecursiveParserState.E){
            if(state == DescendentRecursiveParserState.Q){
                if(pos == sequence.size() + 1 && config.getInputStack().empty()){
                    this.configuration = success();
                    break;
                }else {
                    if (grammar.isInNonTerminals(headInput)) {
                        this.configuration = expand();
                    }else if(grammar.isInTerminals(headInput)){
                        var currentEle = sequence.get(pos - 1);
                        if(Objects.equals(headInput, currentEle)){
                            this.configuration = advance();
                        }else {
                            this.configuration = momentaryInsuccess();
                        }
                    }else {
                        this.configuration = momentaryInsuccess();
                    }
                }

            }
            else {
                if (state == DescendentRecursiveParserState.B){
                    if(grammar.isInTerminals(this.configuration.getWorkingStack().firstElement())){
                        this.configuration = back();
                    }else{
                        this.configuration = anotherTry();
                    }
                }
            }
            config = this.configuration;
            state = config.getState();
            pos = config.getPosition();
            if(config.getInputStack().size() > 0){
                headInput = config.getInputStack().firstElement();
            }else{
                headInput = "";
            }
        }
        return this.configuration;
    }

    @Override
    public List<String> getDerivations() {
        return List.of();
    }
}
