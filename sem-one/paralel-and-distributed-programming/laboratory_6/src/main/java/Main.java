import model.Direction;
import model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var graph = new Graph();
        graph.addNode("1").run();
        graph.addNode("2").run();
        graph.addNode("3").run();
        graph.addNode("4").run();
        graph.addNode("5").run();
        graph.addNode("6").run();
        graph.addNode("7").run();
        graph.addNode("8").run();
        graph.createLink("1", "2").run();
        graph.createLink("3", "1").run();
        graph.createLink("2", "6").run();
        graph.createLink("2", "5").run();
        graph.createLink("6", "7").run();
        graph.createLink("7", "5").run();
        graph.createLink("5", "4").run();
        graph.createLink("5", "8").run();
        graph.createLink("8", "4").run();
        graph.createLink("4", "3").run();
        graph.createLink("7", "8").run();
        graph.createLink("8", "5").run();
        var solutions = new ArrayList<List<String>>();
        solutions.add(Graph.getHamiltonianCycle(graph, new ArrayList<>()));
        System.out.println(solutions);
    }
}
