package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Graph {
    private final List<List<Integer>> container;
    private final List<Integer> nodes;

    public Graph(int nodeCount) {
        this.container = new ArrayList<>(nodeCount);
        this.nodes = new ArrayList<>();

        for (int i = 0; i < nodeCount; i++) {
            this.container.add(new ArrayList<>());
            this.nodes.add(i);
        }
    }

    public void addEdge(int nodeA, int nodeB) {
        this.container.get(nodeA).add(nodeB);
    }

    public List<Integer> neighboursOf(int node) {
        return this.container.get(node);
    }


    public int size() {
        return this.container.size();
    }

}