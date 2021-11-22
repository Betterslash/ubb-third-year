import model.Polynomial;
import model.PolynomialOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var p = new Polynomial(new ArrayList<>(Arrays.asList(5, 4, 2, 4)));
        var q = new Polynomial(new ArrayList<>(Arrays.asList(6, 3, 7)));

        System.out.println("Polinomial one:" + p.toMathInfo());
        System.out.println("Polinomial two:" + q.toMathInfo());
        System.out.println("\n");

        System.out.println(simpleSerial(p, q).toMathInfo() + "\n");
        System.out.println(simpleThreaded(p, q).toMathInfo() + "\n");

        System.out.println(karatsubaSimple(p, q).toMathInfo() + "\n");
        System.out.println(karatsubaThreaded(p, q).toMathInfo() + "\n");
    }

    private static Polynomial karatsubaThreaded(Polynomial p, Polynomial q) throws ExecutionException,
            InterruptedException {
        var startTime = System.currentTimeMillis();
        var result4 = PolynomialOperation.multiplicationKaratsubaParallelizedForm(p, q, 4);
        var endTime = System.currentTimeMillis();
        System.out.println("Karatsuba parallel multiplication of polynomials: ");
        System.out.println("Execution time : " + (endTime - startTime) + " ms");
        return result4;
    }

    private static Polynomial karatsubaSimple(Polynomial p, Polynomial q) {
        var startTime = System.currentTimeMillis();
        var result3 = PolynomialOperation.multiplicationKaratsubaSequentialForm(p, q);
        var endTime = System.currentTimeMillis();
        System.out.println("Karatsuba sequential multiplication of polynomials: ");
        System.out.println("Execution time : " + (endTime - startTime) + " ms");
        return result3;
    }

    private static Polynomial simpleThreaded(Polynomial p, Polynomial q) throws InterruptedException {
        var startTime = System.currentTimeMillis();
        var result2 = PolynomialOperation.multiplicationParallelizedForm(p, q, 5);
        var endTime = System.currentTimeMillis();
        System.out.println("Simple parallel multiplication of polynomials: ");
        System.out.println("Execution time : " + (endTime - startTime) + " ms");
        return result2;
    }

    private static Polynomial simpleSerial(Polynomial p, Polynomial q) {
        var startTime = System.currentTimeMillis();
        var result1 = PolynomialOperation.multiplicationSequentialForm(p, q);
        var endTime = System.currentTimeMillis();
        System.out.println("Simple sequential multiplication of polynomials: ");
        System.out.println("Execution time : " + (endTime - startTime) + " ms");
        return result1;
    }
}
