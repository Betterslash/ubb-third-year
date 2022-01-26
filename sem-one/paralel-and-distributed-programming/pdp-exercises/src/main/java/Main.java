import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //runMatrixMultiply();
        var result = PrimeNumbers.execute(100);
        System.out.println(result);
    }

    private static void runMatrixMultiply() throws InterruptedException {
        var firstMatrix = new int[][] {
                {2, 2, 2, 2, 2},
                {1, 1, 1, 1, 1},
                {3, 3, 3, 3, 3},
                {3, 3, 3, 3, 5}
        };
        var secondMatrix = new int[][] {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
        MatrixesProduct.execute(firstMatrix, secondMatrix)
                .forEach(System.out::println);
    }

    private static void runSubsetsCountiong() throws InterruptedException {
        Subsets aux = new Subsets();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(aux.allSubSets(list));
    }
}
