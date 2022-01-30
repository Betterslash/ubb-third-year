import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

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

        var arr = new Integer[3];
        Back(arr, 0, 3, 4);
    }
}
