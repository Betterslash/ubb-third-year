package model.computations;

import lombok.Getter;
import model.Matrix;
import model.MatrixElement;
import model.Position;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static model.RunningConfiguration.*;

@Getter
public abstract class MatrixElementComputation implements Callable<MatrixElement> {
    public static Matrix firstMatrix;
    public static Matrix secondMatrix;

    protected final Position position;

    protected MatrixElementComputation(Position position) {
        this.position = position;
    }

    public static void initializeMatrixes(){
        firstMatrix = new Matrix(MATRIX_SIZE_ONE, MATRIX_SIZE_TWO);
        secondMatrix = new Matrix(MATRIX_SIZE_TWO, MATRIX_SIZE_THREE);
    }

    @Override
    public MatrixElement call() {
        var lineArray = new ArrayList<Integer>();
        var columnArray = new ArrayList<Integer>();
        var firstMatrixInternal = firstMatrix.getInternal();
        var secondMatrixInternal = secondMatrix.getInternal();
        for (int i = 0; i < MATRIX_SIZE_TWO; i++) {
            lineArray.add(firstMatrixInternal[position.getLine()][i]);
            columnArray.add(secondMatrixInternal[i][position.getColumn()]);
        }
        var result = 0;
        for (int i = 0; i < MATRIX_SIZE_TWO; i++) {
            result += lineArray.get(i) * columnArray.get(i);
        }
        return new MatrixElement(result, position.getLine(), position.getColumn());
    }
}
