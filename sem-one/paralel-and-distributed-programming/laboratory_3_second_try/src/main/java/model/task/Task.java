package model.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import computations.MatrixElementComputation;
import factory.MatrixComputationFactory;
import model.matrix.MatrixElement;
import utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Setter
@Getter
@RequiredArgsConstructor
public class Task implements Callable<List<MatrixElement>> {
    private final List<Position> internal;

    public static Task getInstance(){
        return new Task(new ArrayList<>());
    }

    public List<MatrixElementComputation> getComputations() {
        var results = new ArrayList<MatrixElementComputation>();
        internal.forEach(e -> results.add(MatrixComputationFactory.createComputation(e)));
        return results;
    }


    @Override
    public List<MatrixElement> call() {
        var computations = getComputations();
        var results = new ArrayList<MatrixElement>();
        computations.forEach(e -> results.add(e.call()));
        return results;
    }

    @Override
    public String toString() {
        return "Task{" +
                "internal=" + internal +
                '}';
    }
}
