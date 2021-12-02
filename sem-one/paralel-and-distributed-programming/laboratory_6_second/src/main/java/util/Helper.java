package util;

import model.Graph;
import model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public final class Helper {

    public static Graph generateHamiltonian(int size) {
        var graph = new Graph(size);
        var nodes = graph.getNodes();
        java.util.Collections.shuffle(nodes);

        for (int i = 1; i < nodes.size(); i++){
            graph.addEdge(nodes.get(i - 1),  nodes.get(i));
        }

        graph.addEdge(nodes.get(nodes.size() -1), nodes.get(0));
        var random = new Random();
        for (int i = 0; i < size / 2; i++){
            var nodeA = random.nextInt(size - 1);
            var nodeB = random.nextInt(size - 1);

            graph.addEdge(nodeA, nodeB);
        }
        return graph;
    }

    public static void findHamiltonian(Graph graph, int threadCount) throws InterruptedException {
        var pool = Executors.newFixedThreadPool(threadCount);
        var lock = new ReentrantLock();
        var result = new ArrayList<Integer>(graph.size());
        for (int i = 0; i < graph.size(); i++){
            pool.execute(new Task(graph, i, lock, result));
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
