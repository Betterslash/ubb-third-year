package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Getter
@RequiredArgsConstructor
public class Task implements Runnable {

    private final Graph graph;
    private final int startingNode;
    private final List<Integer> path = new ArrayList<>();
    private final Lock lock;
    private final List<Integer> result;


    @Override
    public void run() {
        visit(startingNode);
    }

    private void setResult() {
        this.lock.lock();
        this.result.clear();
        this.result.addAll(this.path);
        this.lock.unlock();
    }

    private void visit(int node) {
        path.add(node);
        if (path.size() == graph.size()) {
            if (graph.neighboursOf(node).contains(startingNode)){
                setResult();
            }
            return;
        }
        for (int neighbour : graph.neighboursOf(node)) {
            if (!this.path.contains(neighbour)){
                visit(neighbour);
            }
        }
    }
}
