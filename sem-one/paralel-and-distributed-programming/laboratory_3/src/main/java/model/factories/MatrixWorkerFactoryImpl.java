package model.factories;

import model.MatrixWorkerProperties;
import model.adts.PositionPair;
import model.operations.ComputeMatrixElement;
import model.operations.MatrixComputation;
import model.workers.MatrixComputationsWorker;
import model.workers.SerialComputationsWorker;
import model.workers.ThreadedComputationsWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static model.MatrixWorkerProperties.SIZE_ONE;

public class MatrixWorkerFactoryImpl extends MatrixWorkerFactory {

    public MatrixWorkerFactoryImpl(List<PositionPair> positions) {
        super(positions);
    }

    @Override
    public MatrixComputationsWorker createWorker() {
        return switch (MatrixWorkerProperties.WORKER_TYPE){
            case SERIAL -> initializeSerialWorker();
            case THREADED -> initializeThreadedWorker(MatrixWorkerProperties.THREADS_NUMBER);
            case THREAD_POOLED -> initializeThreadPooledWorker(MatrixWorkerProperties.THREADS_NUMBER);
        };
    }

    private MatrixComputationsWorker initializeThreadPooledWorker(int threadsNumber) {
        return null;
    }

    private ThreadedComputationsWorker initializeThreadedWorker(int threadsNumber) {
        return null;
    }

    private SerialComputationsWorker initializeSerialWorker() {
        var operations = new ArrayList<MatrixComputation>();
        AtomicInteger batchNumber = new AtomicInteger();
        positions.forEach(e -> {
            var start = e.getStart();
            var end = e.getEnd();
            for (int i = start.getLine(); i < SIZE_ONE; i++) {
                for (int j = start.getColumn(); j < SIZE_ONE; j++) {
                    operations.add(new ComputeMatrixElement(i, j));
                    if(i == end.getLine() && j == end.getColumn()){
                        System.out.printf("Batch %d finished \n", batchNumber.get());
                        batchNumber.addAndGet(1);
                        break;
                    }
                }
            }
        });
        return new SerialComputationsWorker(operations);
    }
}
