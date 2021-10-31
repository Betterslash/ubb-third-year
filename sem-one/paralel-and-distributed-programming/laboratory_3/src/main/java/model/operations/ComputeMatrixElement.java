package model.operations;

import lombok.RequiredArgsConstructor;
import model.MatrixElement;

import java.util.ArrayList;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class ComputeMatrixElement extends MatrixComputation{
    private final int line;
    private final int column;

    public Callable<MatrixElement> computeElement() {
        return () -> {

            var lineArray = new ArrayList<Integer>();
            var columnArray = new ArrayList<Integer>();
            for (int i = 0; i < firstMatrix.getWidth(); i++) {
                lineArray.add(firstMatrix.getInternal()[line][i]);
            }
            for (int i = 0; i < secondMatrix.getHeight(); i++) {
                columnArray.add(secondMatrix.getInternal()[i][column]);
            }
            var element = 0;
            for (int i = 0; i < lineArray.size(); i++) {
                element += lineArray.get(i) * columnArray.get(i);
            }
            return new MatrixElement(element, line, column);
        };
    }
}
