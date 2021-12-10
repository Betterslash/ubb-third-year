package model;

import java.util.*;
import java.util.stream.Collectors;

public class GenericGraph<T> {
    private final Map<T, GenericDirection<T>> representation = new HashMap<>();

    public Runnable addNode(T node){
        return () -> this.representation.put(node, new GenericDirection<>());
    }

    public Runnable createLink(T from, T to){
        return () -> {
            this.representation.get(from).getOut().add(to);
            this.representation.get(to).getIn().add(from);
        };
    }

    public List<T> getVertexes(){
        return new ArrayList<>(this.representation.keySet());
    }

    public Set<List<T>> getHamiltonians(){
        var possibilities = getPossibilities();
        return possibilities.stream()
                .filter(this::applyHamiltonianCycleCheck)
                .collect(Collectors.toSet());
    }

    private boolean applyHamiltonianCycleCheck(List<T> e) {
        if(e.size() != this.representation.size()){
            return false;
        }else{
            for (int i = 0; i < e.size() - 1; i++) {
                var vertex = e.get(i);
                var next = e.get(i + 1);
                if(!this.representation.get(vertex).getOut().contains(next)){
                    return false;
                }
            }
        }
        return true;
    }

    public Set<List<T>> getPossibilities(){
        var possibilities = getVertexes();
        return new HashSet<>(generatePerm(possibilities));
    }

    public List<List<T>> generatePerm(List<T> original) {
        if (original.isEmpty()) {
            var result = new ArrayList<List<T>>();
            result.add(new ArrayList<>());
            return result;
        }
        var firstElement = original.remove(0);
        var returnValue = new ArrayList<List<T>>();
        var permutations = generatePerm(original);
        for (var smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                var temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
}
