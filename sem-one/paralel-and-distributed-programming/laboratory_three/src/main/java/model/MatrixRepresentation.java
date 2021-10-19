package model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class MatrixRepresentation {
    private final Double[][] internal;
    private final int height;
    private final int width;

    private MatrixRepresentation(Double[][] representation, int height, int width){
        this.internal = representation;
        this.height = height;
        this.width = width;
    }

    public double getElementAt(int line,int column){
        return internal[line][column];
    }

    public Double[] getLine(int line){
        var representation = new Double[width];
        System.arraycopy(internal[line], 0, representation, 0, width);
        return representation;
    }

    public Double[] getColumn(int column){
        var representation = new Double[height];
        for (int i = 0; i < height; i++) {
            representation[i] = internal[i][column];
        }
        return representation;
    }

    public static Pair getPairInstances(){
        var height = ThreadLocalRandom.current().nextInt(10, 100);
        var width = ThreadLocalRandom.current().nextInt(10, 100);
        var firstMatrix = new Double[height][width];
        var secondMatrix = new Double[width][height];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++ ){
                firstMatrix[i][j] = ThreadLocalRandom.current().nextDouble(-1000, 1000);
                secondMatrix[j][i] = ThreadLocalRandom.current().nextDouble(-1000, 1000);
            }
        }
        var matrixOne = new MatrixRepresentation(firstMatrix, height, width);
        var matrixTwo = new MatrixRepresentation(secondMatrix, width, height);
        return new Pair(matrixOne, matrixTwo);
    }

    public static MatrixRepresentation valueOf(Double[][] representation, int height, int width){
        return new MatrixRepresentation(representation, height, width);
    }

    @Override
    public String toString() {
        var body = new StringBuilder();
        var header = "Size -> Height : " + height + " Width : " + width + "\n";
        body.append(header);
        var toBeTrucated = 0d;
        for (int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                toBeTrucated = internal[i][j];
                body.append(BigDecimal.valueOf(toBeTrucated)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue()).append(" ");
            }
            body.append("\n");
        }
        return body.toString();
    }
}
