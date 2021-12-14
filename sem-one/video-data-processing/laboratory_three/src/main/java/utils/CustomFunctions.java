package utils;

import model.block.Block;

import java.util.ArrayList;
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

    public static List<Double> zigZagTransform(double[][] arr, int n, int m) {
        int row = 0, col = 0;
        var result = new ArrayList<Double>();
        // Boolean variable that will true if we
        // need to increment 'row' value otherwise
        // false- if increment 'col' value
        boolean row_inc = false;
        // Print matrix of lower half zig-zag pattern
        int mn = Math.min(m, n);
        for (int len = 1; len <= mn; ++len) {
            for (int i = 0; i < len; ++i) {
                result.add(arr[row][col]);
                if (i + 1 == len)
                    break;
                // If row_increment value is true
                // increment row and decrement col
                // else decrement row and increment
                // col
                if (row_inc) {
                    ++row;
                    --col;
                } else {
                    --row;
                    ++col;
                }
            }
            if (len == mn)
                break;
            // Update row or col value according
            // to the last increment
            if (row_inc) {
                ++row;
                row_inc = false;
            } else {
                ++col;
                row_inc = true;
            }
        }
        // Update the indexes of row and col variable
        if (row == 0) {
            if (col == m - 1)
                ++row;
            else
                ++col;
            row_inc = true;
        } else {
            if (row == n - 1)
                ++col;
            else
                ++row;
            row_inc = false;
        }
        // Print the next half zig-zag pattern
        int MAX = Math.max(m, n) - 1;
        for (int len, diag = MAX; diag > 0; --diag) {
            len = Math.min(diag, mn);
            for (int i = 0; i < len; ++i) {
                result.add(arr[row][col]);
                if (i + 1 == len)
                    break;
                // Update row or col value according
                // to the last increment
                if (row_inc) {
                    ++row;
                    --col;
                } else {
                    ++col;
                    --row;
                }
            }
            // Update the indexes of row and col variable
            if (row == 0 || col == m - 1) {
                if (col == m - 1)
                    ++row;
                else
                    ++col;

                row_inc = true;
            }
            else if (col == 0 || row == n - 1) {
                if (row == n - 1)
                    ++col;
                else
                    ++row;

                row_inc = false;
            }
        }
        return result;
    }

    public static List<Double> applyZigZagTransform(Block block){
        return zigZagTransform(block.getRepresentation(), 8, 8);
    }
}
