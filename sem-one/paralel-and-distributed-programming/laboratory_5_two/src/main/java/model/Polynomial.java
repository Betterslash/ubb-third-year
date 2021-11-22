package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import util.ProgramConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Builder
@Getter
@AllArgsConstructor
public class Polynomial {
    private List<Integer> coefficients;

    public Polynomial(int degree) {
        coefficients = new ArrayList<>(degree + 1);
        var randomGenerator = new Random();
        for (int i = 0; i < degree; i++) {
            coefficients.add(randomGenerator.nextInt(ProgramConfiguration.BOUND));
        }
        coefficients.add(randomGenerator.nextInt(ProgramConfiguration.BOUND) + 1);
    }

    public String toMathInfo(){
        var str = new StringBuilder();
        var power = getDegree();
        for (int i = getDegree(); i >= 0; i--) {
            if ( coefficients.get(i) == 0)
                continue;
            str.append(" ").append(coefficients.get(i)).append("x^").append(power).append(" +");
            power--;
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    public Integer getDegree(){
        return coefficients.size() - 1;
    }

    public int getLength(){
        return coefficients.size();
    }
}
