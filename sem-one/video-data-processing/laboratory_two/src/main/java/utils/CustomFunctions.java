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

    public static Block divide(Block firstMatrix) {
        var result = new double[8][8];
        double aux;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                aux = firstMatrix.getRepresentation()[i][j] / ProgramInitializer.QUANTIZATION_MATRIX[i][j];
                if (aux < 0)
                    result[i][j] = Math.ceil(aux);
                else
                    result[i][j] = Math.floor(aux);
            }
        }
        return Block.builder()
                .representation(result)
                .build();
    }

    public static Block multiply(Block firstMatrix){
        var result = new double[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                result[i][j] = firstMatrix.getRepresentation()[i][j] * ProgramInitializer.QUANTIZATION_MATRIX[i][j];
            }
        }
        return Block.builder()
                .representation(result)
                .build();
    }

    public static Block add(Block block){
        var representation = block.getRepresentation();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                representation[i][j] += 128;
            }
        }
        return Block
                .builder()
                .representation(representation)
                .build();
    }

    public static List<Block> fromListOfMtrixesToListOfBlocks(List<double[][]> matrixes){
        return matrixes
                .stream()
                .map(e -> Block.builder().representation(e).build())
                .collect(Collectors.toList());
    }

    public static Block subtract(Block block){
        var representation = block.getRepresentation();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                representation[i][j] -= 128;
            }
        }
        return Block
                .builder()
                .representation(representation)
                .build();
    }
}
