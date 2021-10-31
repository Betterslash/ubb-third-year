package model.operations;

import model.Matrix;
import model.MatrixElement;

import java.util.concurrent.Callable;

public abstract class MatrixComputation {

    protected static Matrix firstMatrix;
    protected static Matrix secondMatrix;

    public static void initializeComputation(Matrix matrixOne, Matrix matrixTwo){
        firstMatrix = matrixOne;
        secondMatrix = matrixTwo;
    }

    public abstract Callable<MatrixElement> computeElement();
}
