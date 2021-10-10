package model;

import java.util.ArrayList;
import java.util.List;

public class BlockService {

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
                for(int i = 0; i < 8; i++){

                    System.arraycopy(yRepresentation, w, result[i], 0, 8);
                }

                results.add(result);
            }
        }
        return results;
    }

    public static List<int[][]> scaleEightByEight(PPMImage image, ValueSignature signature){
        var results = new ArrayList<int[][]>();
        var toBeScaled = divideInEightByEight(image, signature);
        var sumForAverage = 0;
        for (var value: toBeScaled) {
            var result = new int[4][4];
            for(int p = 0; p < 8; p+=2) {
                for (int t = 0; t < 8; t += 2) {
                    sumForAverage = 0;
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            sumForAverage += value[p + i][t + j];
                        }
                    }
                    result[p/2][t/2] = sumForAverage / 4;
                }
            }
            results.add(result);
        }
        return results;
    }
}
