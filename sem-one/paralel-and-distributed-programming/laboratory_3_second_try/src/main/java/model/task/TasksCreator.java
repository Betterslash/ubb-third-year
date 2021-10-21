package model.task;

import utils.Position;
import utils.RunningConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static utils.RunningConfiguration.THREAD_NUMBER;

public class TasksCreator {
    private static List<Task> getTasksOnRow(int sizeOne, int sizeTwo){
        var elementsPerThread = sizeOne * sizeTwo / THREAD_NUMBER;
        var result = new ArrayList<Task>();
        var currentBatch = Task.getInstance();
        if(elementsPerThread > sizeOne) {
            var counter = 0;
            for (int i = 0; i < sizeOne; i++) {
                for (int j = 0; j < sizeTwo; j++) {
                    if (counter == elementsPerThread) {
                        result.add(currentBatch);
                        currentBatch = Task.getInstance();
                        counter = 0;
                    }
                    currentBatch.getInternal().add(new Position(i, j));
                    counter += 1;
                }
            }
                var index = new AtomicInteger();
                currentBatch.getInternal().forEach((e) -> {
                    if(index.get() < result.size()) {
                        result.get(index.get()).getInternal().add(e);
                        index.addAndGet(1);
                    }else{
                        index.set(0);
                        result.get(index.get()).getInternal().add(e);
                    }
                });

        }else {
            for (int i = 0; i < sizeOne; i++) {
                for (int j = 0; j < sizeTwo; j++) {
                    currentBatch.getInternal().add(new Position(i, j));
                }
                result.add(currentBatch);
                currentBatch = Task.getInstance();
            }
        }
        return result;
    }

    private static List<Task> getTasksOnColumn(int sizeOne, int sizeTwo){
        var elementsPerThread = sizeOne * sizeTwo / THREAD_NUMBER;
        var result = new ArrayList<Task>();
        var currentBatch = Task.getInstance();
        if(elementsPerThread > sizeTwo) {
            var counter = 0;
            for (int i = 0; i < sizeTwo; i++) {
                for (int j = 0; j < sizeOne; j++) {
                    if (counter == elementsPerThread) {
                        result.add(currentBatch);
                        currentBatch = Task.getInstance();
                        counter = 0;
                    }
                    currentBatch.getInternal().add(new Position(j, i));
                    counter += 1;
                }
            }

                var index = new AtomicInteger();
                currentBatch.getInternal().forEach((e) -> {
                    if(index.get() < result.size()) {
                        result.get(index.get()).getInternal().add(e);
                        index.addAndGet(1);
                    }else{
                        index.set(0);
                        result.get(index.get()).getInternal().add(e);
                    }
                });

        }else {
            for (int i = 0; i < sizeTwo; i++) {
                for (int j = 0; j < sizeOne; j++) {
                    currentBatch.getInternal().add(new Position(j, i));
                }
                result.add(currentBatch);
                currentBatch = Task.getInstance();
            }
        }
        return result;
    }

    private static List<Task> getTasksOnKelements(int sizeOne, int sizeTwo){
        var elementsPerThread = sizeOne * sizeTwo / THREAD_NUMBER;
        var result = new ArrayList<Task>();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            result.add(Task.getInstance());
        }
        var counter = 0;
        var taskCounter = 0;
        for (int i = 0; i < sizeOne; i++) {
            for (int j = 0; j < sizeTwo; j++) {
                if(taskCounter == THREAD_NUMBER){
                    taskCounter = 0;
                }
                if(counter < elementsPerThread){
                    result.get(taskCounter)
                            .getInternal()
                            .add(new Position(i, j));
                }
                taskCounter += 1;
            }
        }
        return result;
    }

    public static List<Task> getTasks(){
        return switch (RunningConfiguration.SPLITTING_TYPE){
            case ROW -> getTasksOnRow(RunningConfiguration.MATRIX_SIZE_ONE, RunningConfiguration.MATRIX_SIZE_THREE);
            case COLUMN -> getTasksOnColumn(RunningConfiguration.MATRIX_SIZE_ONE, RunningConfiguration.MATRIX_SIZE_THREE);
            case ELEMENT -> getTasksOnKelements(RunningConfiguration.MATRIX_SIZE_ONE, RunningConfiguration.MATRIX_SIZE_THREE);
        };
    }
}
