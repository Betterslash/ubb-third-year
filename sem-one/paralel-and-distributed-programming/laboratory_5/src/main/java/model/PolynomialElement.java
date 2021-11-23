package model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class PolynomialElement {
    private Integer coefficient;
    private final Integer level;

    public PolynomialElement multiply(PolynomialElement element){
        var level = element.level + this.level;
        var coefficient = element.coefficient * this.coefficient;
        return PolynomialElement
                .builder()
                .level(level)
                .coefficient(coefficient)
                .build();
    }

    public PolynomialElement add(PolynomialElement element){
        var coefficient = element.coefficient + this.coefficient;
        return PolynomialElement
                .builder()
                .level(level)
                .coefficient(coefficient)
                .build();
    }

    public PolynomialElement subtract(PolynomialElement element) {
        var coefficient = this.coefficient - element.coefficient;
        return PolynomialElement
                .builder()
                .level(level)
                .coefficient(coefficient)
                .build();
    }
}
