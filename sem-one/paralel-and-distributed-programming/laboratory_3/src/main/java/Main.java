import model.Matrix;
import model.MatrixElement;
import model.MatrixWorkerProperties;
import model.adts.Position;
import model.adts.PositionPair;
import model.factories.MatrixWorkerFactoryImpl;
import model.operations.MatrixComputation;
import model.workers.MatrixComputationsWorker;

import java.util.ArrayList;

import static model.MatrixWorkerProperties.SIZE_ONE;
import static model.MatrixWorkerProperties.SIZE_TWO;


public class Main {
    public static void main(String[] args) throws Exception {
        MatrixWorkerProperties.initializeProperties();
        var matrixOne = new Matrix(SIZE_ONE, SIZE_TWO);
        var matrixTwo = new Matrix(SIZE_TWO, SIZE_ONE);
        MatrixComputation.initializeComputation(matrixOne, matrixTwo);

        var elementsPerThread = SIZE_ONE * SIZE_ONE / MatrixWorkerProperties.THREADS_NUMBER;
        var counter = 0;
        var positions = new ArrayList<PositionPair>();
        var line = 0;
        var column = 0;
        var matrixElements = new ArrayList<MatrixElement>();
        var start = new Position(0, 0);
        var end = new Position(0, 0);
        for (int i = 0; i < SIZE_ONE; i++) {
            for (int j = 0; j < SIZE_ONE; j++) {
                counter += 1;
                if(counter == elementsPerThread){
                    end = new Position(i, j);
                    positions.add(new PositionPair(start, end));
                    if(j == SIZE_ONE - 1){
                        start = new Position(i + 1, 0);
                    }else{
                        start = new Position(i, j + 1);
                    }
                    counter = 0;
                }
            }
        }
        if(end.getLine() != SIZE_ONE - 1 || end.getColumn() != SIZE_ONE - 1){
            positions.add(new PositionPair(start, new Position(SIZE_ONE - 1, SIZE_ONE - 1)));
        }
        System.out.println(matrixOne);
        System.out.println(matrixTwo);

        var workerFactory = new MatrixWorkerFactoryImpl(positions);
        var elems = workerFactory.createWorker().executeOperations();
        elems.forEach(System.out::println);
    }
}
