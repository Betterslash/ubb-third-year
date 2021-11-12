package utils;

import model.block.Block;

import java.util.List;
import java.util.stream.Collectors;

public final class CustomFunctions {
    public static double alpha(double value){
        if(value == 0){
            return 1 / Math.sqrt(2);
        }else if (value > 0){
            return 1;
        }else{
            throw new RuntimeException(String.format("Value %.2f should not be smaller than 0 !!", value));
        }
    }

    public static double outerSum(double[][] block, int u, int v) {
        var sum = 0.0;
        for (int x = 0; x < 8; x++)
            sum += innerSum(block, u, v, x);
        return sum;
    }

    private static double innerSum(double[][] block, int u, int v, int x) {
        var sum = 0.0;
        for (int y = 0; y < 8; y++)
            sum += product(block[x][y], x, y, u, v);
        return sum;
    }

    private static double product(double value, int x, int y, int u, int v) {
        var cosU = Math.cos((2 * x + 1) * u * Math.PI / 16);
        var cosV = Math.cos((2 * y + 1) * v * Math.PI / 16);
        return value * cosU * cosV;
    }

    public static Block divide(Block firstMatrix) {
        var result = new double[8][8];
        double aux;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                aux = firstMatrix.getRepresentation()[i][j] / ProgramInitializer.QUANTIZATION_MATRIX[i][j];
                if (aux < 0)
                    result[i][j] = Math.ceil(aux);
                else
                    result[i][j] = Math.floor(aux);
            }
        return Block.builder()
                .representation(result)
                .build();
    }

    public static Block multiply(Block firstMatrix){
        var result = new double[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                result[i][j] = firstMatrix.getRepresentation()[i][j] * ProgramInitializer.QUANTIZATION_MATRIX[i][j];
        return Block.builder()
                .representation(result)
                .build();
    }

    public static Block add(Block firstMatrix){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                firstMatrix.getRepresentation()[i][j] += 128;
            }
        }
        return firstMatrix;
    }

    public static List<Block> fromListOfMtrixesToListOfBlocks(List<double[][]> matrixes){
        return matrixes
                .stream()
                .map(e -> Block.builder().representation(e).build())
                .collect(Collectors.toList());
    }
}
