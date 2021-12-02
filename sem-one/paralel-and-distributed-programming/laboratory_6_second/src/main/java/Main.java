import model.Graph;

import java.util.ArrayList;
import java.util.List;

import static util.Helper.findHamiltonian;
import static util.Helper.generateHamiltonian;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        var GRAPHS_COUNT = 101;
        var graphs = new ArrayList<Graph>();
        for (int i = 1; i <= GRAPHS_COUNT; i++) {
            graphs.add(generateHamiltonian(i * 10));
        }

        System.out.println("Sequential");
        testMany(graphs, 1);

        System.out.println("Parallel");
        testMany(graphs, Runtime.getRuntime().availableProcessors());

    }

    private static void testMany(List<Graph> graphs, int threadCount) throws InterruptedException {
        for (int i = 0; i < graphs.size(); i++) {
            test(i, graphs.get(i), threadCount);
        }
    }

    private static void test(int graphNumber, Graph graph, int threadCount) throws InterruptedException {
        var startTime = System.nanoTime();
        findHamiltonian(graph, threadCount);
        var endTime = System.nanoTime();
        var duration = (endTime - startTime) / 1000000;
        if (graphNumber == 1 || graphNumber == 50 || graphNumber == 100) {
            System.out.println("Graph " + graphNumber + ": " + duration + " ms");
        }
    }

}