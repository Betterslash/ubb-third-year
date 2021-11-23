import model.Polynomial;
import utils.Initializer;

import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Initializer.initialize();
        var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var polynomialOne = new Polynomial(executor);
        var polynomialTwo = new Polynomial(executor);
        System.out.println(polynomialOne.toMathForm());
        System.out.println(polynomialTwo.toMathForm());
        var polynomialThree = Polynomial.multiplicationKaratsubaSequentialForm(polynomialOne, polynomialTwo);
        System.out.println(polynomialThree.toMathForm());
        System.out.println(polynomialThree.getDegree());
    }
}
