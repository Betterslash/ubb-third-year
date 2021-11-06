package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DecoderBlockService {

    private static void printExpandedList(List<int[][]> result){
        for (var ints : result) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(ints[i][j] + " ");
                }
                System.out.print(System.lineSeparator());
            }
            System.out.print(System.lineSeparator());
        }
    }

    public static List<double[][]> expandUV(List<double[][]> uvParam){
        var size = uvParam.size();
        var result = new ArrayList<double[][]>();
        for (int i = 0; i < uvParam.size(); i++) {
            result.add(new double[8][8]);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.get(i)[j * 2][k * 2] = uvParam.get(i)[j][k];
                    result.get(i)[j * 2 + 1][k * 2] = uvParam.get(i)[j][k];
                    result.get(i)[j * 2][k * 2 + 1] = uvParam.get(i)[j][k];
                    result.get(i)[j * 2 + 1][k * 2 + 1] = uvParam.get(i)[j][k];
                }
            }
        }
        return result;
    }

    private static PPMImage createImageResult(List<double[][]> yValues, List<double[][]> uValues, List<double[][]> vValues){
        var image = PPMImage.PPMImageBuilder.Build();
        image.setWidth(800);
        image.setHeigth(600);
        image.setY(decodeBlock(yValues));
        image.setU(decodeBlock(uValues));
        image.setV(decodeBlock(vValues));
        image.initializeRGB();
        return image;
    }

    public static void decodeImage(List<double[][]> yValues, List<double[][]> uValues, List<double[][]> vValues) throws IOException {
        var imageResult = createImageResult(yValues, uValues, vValues);
        recomposeImage(imageResult);
    }

    /**
     * @param blocks For every block in the list I put it in a cretain order to keep its position in the image
     * @return decoded matrix coresponding to the block type introduced
     */
    private static double[][] decodeBlock(List<double[][]> blocks){
        double[][] matrix = new double[600][800];
        int line = 0;
        int column = 0;
        for (var block : blocks)
        {
            for (int i = 0; i < 8; i++)
                System.arraycopy(block[i], 0, matrix[line + i], column, 8);
            column += 8;
            if (column == 800)
            {
                line += 8;
                column = 0;
            }
        }

        return matrix;
    }

    private static void recomposeImage(PPMImage image) throws IOException {
        var writer = new BufferedWriter(new FileWriter("image.ppm"));
        writer.write("P3" + System.lineSeparator());
        writer.write("# CREATOR: GIMP PNM Filter Version 1.1" + System.lineSeparator());
        writer.write("800 600" + System.lineSeparator());
        writer.write("255" + System.lineSeparator());
        for (int i = 0; i < 600; i++) {
            for (int j = 0; j < 800; j++) {
                writer.write(image.getR()[i][j] + System.lineSeparator());
                writer.write(image.getG()[i][j] + System.lineSeparator());
                writer.write(image.getB()[i][j] + System.lineSeparator());
            }
        }
        writer.close();
    }
}
