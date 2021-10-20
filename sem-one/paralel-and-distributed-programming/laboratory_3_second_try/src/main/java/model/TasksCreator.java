package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TasksCreator {
    private static List<? extends List<Position>> getTasksOnRow(int sizeOne, int sizeTwo){
        var elementsPerThread = sizeOne * sizeTwo / RunningConfiguration.THREAD_NUMBER;
        var result = new ArrayList<ArrayList<Position>>();
        var currentBatch = new ArrayList<Position>();
        if(elementsPerThread > sizeOne) {
            var counter = 0;
            for (int i = 0; i < sizeOne; i++) {
                for (int j = 0; j < sizeTwo; j++) {
                    if (counter == elementsPerThread) {
                        result.add(currentBatch);
                        currentBatch = new ArrayList<>();
                        counter = 0;
                    }
                    currentBatch.add(new Position(i, j));
                    counter += 1;
                }
            }
                var index = new AtomicInteger();
                currentBatch.forEach((e) -> {
                    if(index.get() < result.size()) {
                        result.get(index.get()).add(e);
                        index.addAndGet(1);
                    }else{
                        index.set(0);
                        result.get(index.get()).add(e);
                    }
                });

        }else {
            for (int i = 0; i < sizeOne; i++) {
                for (int j = 0; j < sizeTwo; j++) {
                    currentBatch.add(new Position(i, j));
                }
                result.add(currentBatch);
                currentBatch = new ArrayList<>();
            }
        }
        return result;
    }

    private static List<? extends List<Position>> getTasksOnColumn(int sizeOne, int sizeTwo){
        var elementsPerThread = sizeOne * sizeTwo / RunningConfiguration.THREAD_NUMBER;
        var result = new ArrayList<ArrayList<Position>>();
        var currentBatch = new ArrayList<Position>();
        if(elementsPerThread > sizeTwo) {
            var counter = 0;
            for (int i = 0; i < sizeTwo; i++) {
                for (int j = 0; j < sizeOne; j++) {
                    if (counter == elementsPerThread) {
                        result.add(currentBatch);
                        currentBatch = new ArrayList<>();
                        counter = 0;
                    }
                    currentBatch.add(new Position(j, i));
                    counter += 1;
                }
            }

                var index = new AtomicInteger();
                currentBatch.forEach((e) -> {
                    if(index.get() < result.size()) {
                        result.get(index.get()).add(e);
                        index.addAndGet(1);
                    }else{
                        index.set(0);
                        result.get(index.get()).add(e);
                    }
                });

        }else {
            for (int i = 0; i < sizeTwo; i++) {
                for (int j = 0; j < sizeOne; j++) {
                    currentBatch.add(new Position(j, i));
                }
                result.add(currentBatch);
                currentBatch = new ArrayList<>();
            }
        }
        return result;
    }

    public static List<? extends List<Position>> getTasks(){
        return switch (RunningConfiguration.SPLITTING_TYPE){
            case ROW -> getTasksOnRow(RunningConfiguration.MATRIX_SIZE_ONE, RunningConfiguration.MATRIX_SIZE_THREE);
            case COLUMN -> getTasksOnColumn(RunningConfiguration.MATRIX_SIZE_ONE, RunningConfiguration.MATRIX_SIZE_THREE);
            case ELEMENT -> null;
        };
    }
}
