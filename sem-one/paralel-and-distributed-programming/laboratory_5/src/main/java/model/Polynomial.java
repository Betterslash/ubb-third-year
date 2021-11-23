package model;

import lombok.*;
import utils.Initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Polynomial {

    private final List<PolynomialElement> coefficients;
    private Integer degree;
    private ExecutorService executorService;

    public Polynomial(){
        var random = ThreadLocalRandom.current();
        degree = random.nextInt(2, Initializer.POLYNOMIAL_MAX_LENGTH);
        coefficients = generateCoefficients(random);
    }

    public Polynomial(ExecutorService executor){
        var random = ThreadLocalRandom.current();
        degree = random.nextInt(2, Initializer.POLYNOMIAL_MAX_LENGTH);
        coefficients = generateCoefficients(random);
        executorService = executor;
    }

    private List<PolynomialElement> generateCoefficients(ThreadLocalRandom random){
        var result = new ArrayList<PolynomialElement>();
        for (int i = 0; i < degree + 1; i++) {
            result.add(PolynomialElement.builder()
                            .coefficient(random.nextInt(1, Initializer.POLYNOMIAL_MAX_VALUE))
                            .level(i)
                    .build());
        }
        return result;
    }

    public Polynomial multiply(Polynomial polynomial){
        var elements = new ArrayList<PolynomialElement>();
        var maxLevel = -1;
        for (int i = 0; i < this.coefficients.size(); i++) {
            for (int j = 0; j < polynomial.coefficients.size(); j++) {
                var result = polynomial.coefficients.get(j).multiply(this.coefficients.get(i));
                elements.add(result);
                if(result.getLevel() > maxLevel){
                    maxLevel = result.getLevel();
                }
            }
        }
        return Polynomial.builder()
                .coefficients(elements)
                .degree(maxLevel)
                .build();
    }

    public Polynomial multiplyAsync(Polynomial polynomial){
        var elements = new ArrayList<PolynomialElement>();
        var maxLevel = new AtomicReference<Integer>();
        maxLevel.set(-1);
        for (int i = 0; i < this.coefficients.size(); i++) {
            for (int j = 0; j < polynomial.coefficients.size(); j++) {
                int finalJ = j;
                int finalI = i;
                executorService.submit(() -> {
                    var result = polynomial.coefficients
                            .get(finalJ)
                            .multiply(this.coefficients
                                    .get(finalI));
                    elements.add(result);
                    if(result.getLevel() > maxLevel.get()){
                        maxLevel.set(result.getLevel());
                    }
                });
            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return Polynomial.builder()
                .coefficients(elements)
                .degree(maxLevel.get())
                .build();
    }

    public String toMathForm(){
        var result = new StringBuilder();
        var coefficientsSize = this.coefficients.size() - 1;
        for (int i = coefficientsSize; i >= 1; i--) {
            result.append(String.format("%dx^%d + ", coefficients.get(i).getCoefficient(), coefficients.get(i).getLevel()));
        }
        result.append(String.format("%dx^%d", coefficients.get(0).getCoefficient(), coefficients.get(0).getLevel()));
        return result.toString();
    }

    public static Polynomial multiplicationKaratsubaSequentialForm(Polynomial p1, Polynomial p2) {
        if(p1.getDegree() < 2 || p2.getDegree() < 2){
            return p1.multiply(p2);
        }
        int len = Math.max(p1.getDegree() - 1, p2.getDegree() - 1) / 2;
        var lowP1 = Polynomial.builder()
                .coefficients(p1.getCoefficients().subList(0, len))
                .build();
        var highP1 = Polynomial.builder()
                .coefficients(p1.getCoefficients().subList(len, p1.coefficients.size()))
                .build();
        var lowP2 = Polynomial.builder()
                .coefficients(p2.getCoefficients().subList(0, len))
                .build();
        var highP2 = Polynomial.builder()
                .coefficients(p2.getCoefficients().subList(len, p2.coefficients.size()))
                .build();

        lowP1.refreshDegree();
        lowP2.refreshDegree();
        highP1.refreshDegree();
        highP2.refreshDegree();

        var z1 = multiplicationKaratsubaSequentialForm(lowP1, lowP2);
        var z2 = multiplicationKaratsubaSequentialForm(lowP1.add(highP1), lowP2.add(highP2));
        var z3 = multiplicationKaratsubaSequentialForm(highP1, highP2);

        //calculate the final result
        var r1 = shift(z3, 2 * len);
        var sbs = z2.subtract(z3);
        var r2 = shift(sbs.subtract(z1), len);
        return z1.add(r1.add(r2));
    }

    public static Polynomial shift(Polynomial p, int offset) {
        var coefficients = new ArrayList<PolynomialElement>();
        for (int i = 0; i < offset; i++) {
            coefficients.add(PolynomialElement.builder().coefficient(0).level(0).build());
        }
        for (int i = 0; i < p.coefficients.size(); i++) {
            coefficients.add(PolynomialElement.builder()
                            .level(p.coefficients.get(i).getLevel())
                    .coefficient(p.coefficients.get(i).getCoefficient()).build());
        }
        var result = Polynomial.builder().coefficients(coefficients).build();
        result.refreshDegree();
        return result;
    }


    public Polynomial add(Polynomial polynomial) {
        var result = Polynomial.builder()
                .coefficients(new ArrayList<>())
                .build();
        if(this.coefficients.size() > polynomial.coefficients.size()) {
            this.coefficients.forEach(e -> {
                var coeff = polynomial.coefficients.stream()
                        .filter(q -> Objects.equals(q.getLevel(), e.getLevel()))
                        .collect(Collectors.toList());
                if (coeff.size() > 0) {
                    result.coefficients.add(e.add(coeff.get(0)));
                } else {
                    result.coefficients.add(e);
                }
            });
        }else{
            polynomial.coefficients.forEach(e -> {
                var coeff = this.coefficients.stream()
                        .filter(q -> Objects.equals(q.getLevel(), e.getLevel()))
                        .collect(Collectors.toList());
                if (coeff.size() > 0) {
                    result.coefficients.add(e.add(coeff.get(0)));
                } else {
                    result.coefficients.add(e);
                }
            });
        }
        result.refreshDegree();
        return result;
    }

    public Polynomial subtract(Polynomial polynomial)  {
        var result = Polynomial.builder().coefficients(new ArrayList<>()).build();
        this.coefficients.forEach(e -> {
            var toBeSubtractedFrom = polynomial.coefficients.stream()
                    .filter(p -> Objects.equals(p.getLevel(), e.getLevel()))
                    .collect(Collectors.toList());
            if(toBeSubtractedFrom.size() > 0){
                result.coefficients.add(e.subtract(toBeSubtractedFrom.get(0)));
            }
        });
        var thisCoeff = this.coefficients.stream()
                .filter(p -> polynomial.coefficients.stream().noneMatch(a -> Objects.equals(a.getLevel(), p.getLevel())))
                .collect(Collectors.toList());
        var polCoeff = polynomial.coefficients.stream()
                .filter(p -> this.coefficients.stream().noneMatch(a -> Objects.equals(a.getLevel(), p.getLevel())))
                .collect(Collectors.toList());
        result.coefficients.addAll(thisCoeff);
        polCoeff.forEach(a -> a.setCoefficient(a.getCoefficient() * -1));
        result.coefficients.addAll(polCoeff);
        result.refreshDegree();
        return result;
    }

    private int getRefreshedDegree(){
        var lastIndex = this.coefficients.size() - 1;
        return Math.max(lastIndex, 0);
    }

    public void refreshDegree(){
        this.degree = getRefreshedDegree();
    }
}
