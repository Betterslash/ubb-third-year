package model;

import model.task.MultiplicationTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialOperation {

    public static Polynomial multiplicationSequentialForm(Polynomial p1, Polynomial p2) {
        var sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
        var coefficients = new ArrayList<Integer>();
        for (int i = 0; i < sizeOfResultCoefficientList; i++) {
            coefficients.add(0);
        }
        for (int i = 0; i < p1.getCoefficients().size(); i++) {
            for (int j = 0; j < p2.getCoefficients().size(); j++) {
                int index = i + j;
                int value = p1.getCoefficients().get(i) * p2.getCoefficients().get(j);
                coefficients.set(index, coefficients.get(index) + value);
            }
        }
        return new Polynomial(coefficients);
    }


    public static Polynomial multiplicationParallelizedForm(Polynomial p1, Polynomial p2, int nrOfThreads) throws
            InterruptedException {
        //initialize result polynomial with coefficients = 0
        var sizeOfResultCoefficientList = p1.getDegree() + p2.getDegree() + 1;
        var coefficients = IntStream.of(new int[sizeOfResultCoefficientList]).boxed().collect(Collectors
                .toList());
        var result = new Polynomial(coefficients);

        //calculate the coefficients

        var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nrOfThreads);
        var step = result.getLength() / nrOfThreads;
        if (step == 0) {
            step = 1;
        }
        //System.out.println("STEP: " + step);
        int end;
        for (int i = 0; i < result.getLength(); i += step) {
            end = i + step;
            var task = new MultiplicationTask(i, end, p1, p2, result);
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(50, TimeUnit.SECONDS);

        return result;
    }

    public static Polynomial multiplicationKaratsubaSequentialForm(Polynomial p1, Polynomial p2) {
        if (p1.getDegree() < 2 || p2.getDegree() < 2) {
            return multiplicationSequentialForm(p1, p2);
        }

        var len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        var lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        var highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getLength()));
        var lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        var highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getLength()));

        var z1 = multiplicationKaratsubaSequentialForm(lowP1, lowP2);
        var z2 = multiplicationKaratsubaSequentialForm(add(lowP1, highP1), add(lowP2, highP2));
        var z3 = multiplicationKaratsubaSequentialForm(highP1, highP2);

        //calculate the final result
        var r1 = shift(z3, 2 * len);
        var r2 = shift(subtract(subtract(z2, z3), z1), len);
        return add(add(r1, r2), z1);
    }

    public static Polynomial multiplicationKaratsubaParallelizedForm(Polynomial p1, Polynomial p2, int currentDepth)
            throws ExecutionException, InterruptedException {
        //E impartit deja de 4 ori si pentru ca e recursiv, nu mai ??mpar??im in mai mult pt ca nu ??ncape pe stack-ul
        // intern
        if (currentDepth > 4) {
            return multiplicationKaratsubaSequentialForm(p1, p2);
        }

        if (p1.getDegree() < 2 || p2.getDegree() < 2) {
            return multiplicationKaratsubaSequentialForm(p1, p2);
        }

        var len = Math.max(p1.getDegree(), p2.getDegree()) / 2;
        var lowP1 = new Polynomial(p1.getCoefficients().subList(0, len));
        var highP1 = new Polynomial(p1.getCoefficients().subList(len, p1.getLength()));
        var lowP2 = new Polynomial(p2.getCoefficients().subList(0, len));
        var highP2 = new Polynomial(p2.getCoefficients().subList(len, p2.getLength()));

        var executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Callable<Polynomial> task1 = () -> multiplicationKaratsubaParallelizedForm(lowP1, lowP2, currentDepth + 1);
        Callable<Polynomial> task2 = () -> multiplicationKaratsubaParallelizedForm(PolynomialOperation.add(lowP1, highP1), PolynomialOperation
                .add(lowP2, highP2), currentDepth + 1);
        Callable<Polynomial> task3 = () -> multiplicationKaratsubaParallelizedForm(highP1, highP2, currentDepth);

        var f1 = executor.submit(task1);
        var f2 = executor.submit(task2);
        var f3 = executor.submit(task3);

        executor.shutdown();

        Polynomial z1 = f1.get();
        Polynomial z2 = f2.get();
        Polynomial z3 = f3.get();

        executor.awaitTermination(60, TimeUnit.SECONDS);

        var r1 = shift(z3, 2 * len);
        var r2 = shift(subtract(subtract(z2, z3), z1), len);
        return add(add(r1, r2), z1);
    }


    public static Polynomial shift(Polynomial p, int offset) {
        List<Integer> coefficients = new ArrayList<>();
        for (int i = 0; i < offset; i++) {
            coefficients.add(0);
        }
        for (int i = 0; i < p.getLength(); i++) {
            coefficients.add(p.getCoefficients().get(i));
        }
        return new Polynomial(coefficients);
    }


    public static Polynomial add(Polynomial p1, Polynomial p2) {
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) + p2.getCoefficients().get(i));
        }

        addRemainingCoefficients(p1, p2, minDegree, maxDegree, coefficients);

        return new Polynomial(coefficients);
    }


    private static void addRemainingCoefficients(Polynomial p1, Polynomial p2, int minDegree, int maxDegree,
                                                 List<Integer> coefficients) {
        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p1.getCoefficients().get(i));
                }
            } else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p2.getCoefficients().get(i));
                }
            }
        }
    }


    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        var minDegree = Math.min(p1.getDegree(), p2.getDegree());
        var maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        var coefficients = new ArrayList<Integer>(maxDegree + 1);

        //Subtract the 2 polynomials
        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) - p2.getCoefficients().get(i));
        }

        addRemainingCoefficients(p1, p2, minDegree, maxDegree, coefficients);

        //remove coefficients starting from biggest power if coefficient is 0

        int i = coefficients.size() - 1;
        while (coefficients.get(i) == 0 && i > 0) {
            coefficients.remove(i);
            i--;
        }

        return new Polynomial(coefficients);
    }
}
