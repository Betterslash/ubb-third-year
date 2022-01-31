import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Main {
    public static void Back(Integer[] arr, int position, int length, int N){
        if(position == length){
            var lista = new HashSet<>(Arrays.asList(arr));
            if((lista.size() == length) && !lista.contains(0)){
                for (int j : arr) {
                    System.out.print(j + " ");
                }
                System.out.println();
            }
        }
        else{
            for (int i = 0; i <= N; i++ ){
                arr[position] = i;
                Back(arr, position + 1, length, N);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var t1 = new Thread(() -> System.out.println("ok"));
        var t2 = new Thread(() -> System.out.println("Muie Lupsa"));
        var arr = List.of(t1, t2);
        arr.forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }
}
