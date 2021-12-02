package model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class Graph {
    private final Map<String, Direction> representation = new HashMap<>();

    public Runnable addNode(String node){
        return () -> this.representation.put(node, new Direction());
    }

    public Runnable createLink(String from, String to){
        return () -> {
            this.representation.get(from).getOut().add(to);
            this.representation.get(to).getIn().add(from);
        };
    }

    public List<String> getVertexes(){
        return new ArrayList<>(this.representation.keySet());
    }

    public Set<List<String>> getHamiltonians(){
        var possibilities = getPossibilities();
        return possibilities.stream()
                .filter(this::applyHamiltonianCycleCheck)
                .collect(Collectors.toSet());
    }

    private boolean applyHamiltonianCycleCheck(List<String> e) {
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

    public Set<List<String>> getPossibilities(){
        var possibilities = getVertexes();
        return new HashSet<>(generatePerm(possibilities));
    }

    public <E> List<List<E>> generatePerm(List<E> original) {
        if (original.isEmpty()) {
            var result = new ArrayList<List<E>>();
            result.add(new ArrayList<>());
            return result;
        }
        var firstElement = original.remove(0);
        var returnValue = new ArrayList<List<E>>();
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
