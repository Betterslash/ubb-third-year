package model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class Matrix {
    private int[][] internal;
    private final int height;
    private final int width;

    public Matrix(int height, int width){
        this.height = height;
        this.width = width;
        intializeInternal();
    }

    private void intializeInternal() {
        internal = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.internal[i][j] = ThreadLocalRandom.current().nextInt(-10, 10);
            }
        }
    }

    public Matrix(int[][] representation, int height, int width){
        this.internal = representation;
        this.height = height;
        this.width = width;
    }

    @Override
    public String toString() {
        var header = "Sizes -> Height : " + height + " Width : " + width + System.lineSeparator();
        var body = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                body.append(internal[i][j])
                        .append(" ");
            }
            body.append(System.lineSeparator());
        }
        return header + body;
    }
}
