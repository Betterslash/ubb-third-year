package model;

import java.util.ArrayList;
import java.util.List;

public final class EncoderBlockService {

    private static double[][] reduce(ValueSignature signature, PPMImage image){
        switch (signature){
            case Y -> {
                return image.getY();
            }
            case U -> {
                return image.getU();
            }
            case V -> {
                return image.getV();
            }
            default -> throw new RuntimeException("Value must be either Y, U, V");
        }
    }

    public static List<double[][]> divideInEightByEight(PPMImage image, ValueSignature signature){
        var heigth = image.getHeigth();
        var width = image.getWidth();
        var yRepresentation = reduce(signature, image);
        var results = new ArrayList<double[][]>();
        for(int h = 0; h < heigth; h+=8){
            for(int w = 0; w < width; w+=8){
                var result = new double[8][8];
                for (int i = 0; i < 8; i++) {
                    System.arraycopy(yRepresentation[i + h], w, result[i], 0, 8);
                }
                results.add(result);
            }
        }
        return results;
    }

    public static List<double[][]> scaleEightByEight(PPMImage image, ValueSignature signature){
        var results = new ArrayList<double[][]>();
        var toBeScaled = divideInEightByEight(image, signature);
        for (var value: toBeScaled) {
            var elements = new ArrayList<Double>();
            var result = new double[4][4];
            for(int p = 0; p < 8; p+=2) {
                for (int t = 0; t < 8; t+=2) {
                    var sumForAverage = 0d;
                    sumForAverage += value[p][t];
                    sumForAverage += value[p][t+1];
                    sumForAverage += value[p+1][t];
                    sumForAverage += value[p+1][t+1];
                    elements.add(sumForAverage / 4);
                }
            }
            var index = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    result[i][j] = elements.get(index);
                    index += 1;
                }
            }
            results.add(result);
        }
        return results;
    }
}
