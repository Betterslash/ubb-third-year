package model.factory;

import model.RunningConfiguration;
import model.WorkerType;
import model.worker.MatrixWorker;
import model.worker.SerialMatrixWorker;
import model.worker.ThreadPooledMatrixWorker;
import model.worker.ThreadedMatrixWorker;

public final class WorkerFactory {
    public static MatrixWorker createWorker(){
        MatrixWorker worker;
        if (RunningConfiguration.WORKER_TYPE == WorkerType.SERIAL) {
            worker = new SerialMatrixWorker();
            return worker;
        }
        if (RunningConfiguration.WORKER_TYPE == WorkerType.THREADED) {
            worker = new ThreadedMatrixWorker();
            return worker;
        }
        if(RunningConfiguration.WORKER_TYPE == WorkerType.THREAD_POOLED){
            worker = new ThreadPooledMatrixWorker();
            return worker;
        }
        throw new IllegalArgumentException();
    }
}
