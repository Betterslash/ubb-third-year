package service.lr_zero_parser;

import lombok.Getter;
import lombok.Setter;
import model.Grammar;
import model.Production;
import service.Parser;
import service.ProductionMapKey;
import service.lr_zero_parser.table.LRZeroTable;
import utils.Constants;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
public class LRZeroParser extends Parser {

    private final Map<ProductionMapKey, List<String>> mappedProductions;
    private LRZeroTable lrZeroTable;

    public LRZeroParser(Grammar grammar) {
        super(grammar);
        this.mappedProductions = initializeMappedProductions();
    }

    private Map<ProductionMapKey, List<String>> initializeMappedProductions() {
        var result = new HashMap<ProductionMapKey, List<String>>();
        var index = new AtomicInteger(0);
        grammar.getP()
                .forEach(e -> e.getRightHandside().forEach(q -> {
                    var key = ProductionMapKey.builder()
                            .productionLeftHandside(e.getLeftHandside())
                            .productionNumber(index.get())
                            .build();
                    result.put(key, q);
                    index.set(index.get() + 1);
                }));
        return result;
    }

    private List<LRZeroStateProduction> fromProductionToLRZeroProduction(Production production){
        if(production == null){
           return null;
        }
        return production.getRightHandside()
                .stream()
                .map(e -> {
                    var productionValue = new ArrayList<>(e);
                    productionValue.add(0, Constants.LR_ZERO_DOT);
                   return LRZeroStateProduction.builder()
                           .leftHandside(production.getLeftHandside())
                           .rightHandside(productionValue)
                           .build();
                })
                .collect(Collectors.toList());
    }

    private LRZeroState closure(LRZeroStateProduction production){
        if(production == null){
            return null;
        }
        var dotIndex = production.getRightHandside().indexOf(Constants.LR_ZERO_DOT);
        if(dotIndex + 1 > production.getRightHandside().size() - 1){
            return LRZeroState.builder()
                    .lrZeroStateProduction(List.of(production))
                    .build();
        }
        var nextSymbolAfterDot = production.getRightHandside().get(dotIndex + 1);
        if(grammar.isInNonTerminals(nextSymbolAfterDot)){
            var productionsOfNextSymbolAfterDot = grammar.getP()
                    .stream()
                    .filter(e -> Objects.equals(e.getLeftHandside(), nextSymbolAfterDot))
                    .findFirst()
                    .orElse(null);
            assert productionsOfNextSymbolAfterDot != null;
            var stateProductions = fromProductionToLRZeroProduction(productionsOfNextSymbolAfterDot);
            if(stateProductions == null){
                return null;
            }else{
                stateProductions.add(production);
            }
            return LRZeroState.builder()
                    .lrZeroStateProduction(stateProductions)
                    .build();
        }else{
            return LRZeroState.builder()
                    .lrZeroStateProduction(List.of(production))
                    .build();
        }
    }

    private LRZeroStateProduction goTo(LRZeroState state, String symbol){
        if(state == null){
            return null;
        }
        var productions = state.copy().getLrZeroStateProduction();
        var production = productions.stream()
                .filter(e -> e.getRightHandside().indexOf(Constants.LR_ZERO_DOT) ==  e.getRightHandside().indexOf(symbol) - 1)
                .findFirst()
                .orElse(null);
        if(production == null){
            return null;
        }
        var dotIndex = production.getRightHandside().indexOf(Constants.LR_ZERO_DOT);
        var symbolIndex = production.getRightHandside().indexOf(symbol);
        if(dotIndex == symbolIndex - 1) {
            var prodCopy = production.copy();
            Collections.swap(prodCopy.getRightHandside(), dotIndex, symbolIndex);
            return prodCopy;
        }else{
            return null;
        }
    }

    public void parse(){
        var upgradedSymbol = grammar.getS() + "'";
        var startProductionRightHandside = new ArrayList<>(Arrays.asList(Constants.LR_ZERO_DOT, grammar.getS()));
        var upgradedProduction = LRZeroStateProduction
                .builder()
                .rightHandside(startProductionRightHandside)
                .leftHandside(upgradedSymbol)
                .build().copy();
        var results = new HashSet<LRZeroState>();
        var initialState = closure(upgradedProduction);
        results.add(initialState.copy());
        var canonicalCollectionSize = results.size();
        var allSymbols = new ArrayList<>(grammar.getN());
        allSymbols.addAll(grammar.getE());
        do {
            var resultsCopy = new HashSet<>(results);
            canonicalCollectionSize = results.size();
            for (var element: resultsCopy) {
                allSymbols.forEach(e -> {
                    if(element != null){
                        var elementCopy = element.copy();
                        var lrZeroProd = goTo(elementCopy, e);
                        if(lrZeroProd != null){
                            var productionCopy = lrZeroProd.copy();
                            var state = closure(productionCopy);
                            if (state != null) {
                                var stateCopy = state.copy();
                                if(state.notAlreadyIn(results)){
                                    results.add(stateCopy);
                                }
                            }
                        }
                    }
                });
            }
        } while (canonicalCollectionSize != results.size());
        results.forEach(e -> {
            System.out.println();
            e.getLrZeroStateProduction().forEach(System.out::println);
            System.out.println();
        });
    }

    @Override
    protected List<String> getDerivations() {
        return null;
    }
}
