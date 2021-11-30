package model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<String> getHamiltonianCycle(Graph graph, List<String> path){
        if(path.size() == graph.representation.size() && graph.representation.get(path.get(0)).getIn().contains(path.get(path.size() - 1))){
            return path;
        }else {
            for (var el: graph.representation.entrySet()) {
                if (!path.contains(el.getKey())) {
                    if(path.size() > 0){
                        var previouslyAddedNode = path.get(path.size() - 1);
                        if(el.getValue().getIn().contains(previouslyAddedNode)){
                            path.add(el.getKey());
                            return getHamiltonianCycle(graph, path);
                        }
                    }else {
                        path.add(el.getKey());
                        return getHamiltonianCycle(graph, path);
                    }
                }
            }
            return getHamiltonianCycle(graph, path.subList(1, path.size()));
        }
    }
}
