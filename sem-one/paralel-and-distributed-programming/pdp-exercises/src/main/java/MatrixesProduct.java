import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixesProduct {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public static boolean areMultipliable(int[][] firstMatrix, int[][] secondMatrix){
        var firstWidth = firstMatrix[0].length;
        var secondHeigth = secondMatrix.length;
        return firstWidth == secondHeigth;
    }

    public static Integer multiply(int[] firstLine, Integer[] secondColumn){
            var result = new ArrayList<Integer>();
            for (int i = 0; i < firstLine.length; i++) {
                result.add(firstLine[i] * secondColumn[i]);
            }
            return result.stream().reduce(Integer::sum).orElse(0);
    }

    public static Integer[] getColumn(int[][] matrix, int columnNumber){
        var result = new ArrayList<Integer>();
        for (int[] ints : matrix) {
            result.add(ints[columnNumber]);
        }
        return result.toArray(new Integer[0]);
    }

    public static List<List<Integer>> execute(int[][] firstMatrix, int[][] secondMatrix) throws InterruptedException {
        if(areMultipliable(firstMatrix, secondMatrix)){
            var result = new ArrayList<List<Integer>>();
            var tasks = new ArrayList<Thread>();
            int i = 0;
            for (int j = 0; j < firstMatrix.length; j++) {
                result.add(new ArrayList<>());
            }
            for (int[] firstMatrixLine : firstMatrix) {
                int finalI = i;
                var task = new Thread(() -> result.set(finalI, getMultiplyLineResult(firstMatrixLine, secondMatrix)));
                i++;
                tasks.add(task);
            }
            tasks.forEach(executorService::submit);
            executorService.shutdown();
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
            return result;
        }else {
            throw new RuntimeException();
        }
    }

    public static List<Integer> getMultiplyLineResult(int[] firstMatrixLine, int[][] secondMatrix){
        var line = new ArrayList<Integer>();
        for (int j = 0; j < secondMatrix[0].length; j++) {
            var secondColumn = getColumn(secondMatrix, j);
            line.add(multiply(firstMatrixLine, secondColumn));
        }
        return line;
    }
}
