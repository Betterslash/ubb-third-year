package service;

import model.PPMImage;
import model.block.Block;
import repository.AbstractBlockRepository;
import repository.BlockRepository;
import repository.BlockRepositoryImpl;
import repository.GBlockRepository;
import utils.CustomFunctions;
import utils.ProgramInitializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static utils.CustomFunctions.alpha;

public final class DecoderBlockService {

    public static BlockRepository expandUV(BlockRepository uvParam){
        var size = uvParam.getStorage().size();
        var result = new ArrayList<double[][]>();
        for (int i = 0; i < uvParam.getStorage().size(); i++) {
            result.add(new double[8][8]);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.get(i)[j * 2][k * 2] = uvParam.getStorage().get(i).getRepresentation()[j][k];
                    result.get(i)[j * 2 + 1][k * 2] = uvParam.getStorage().get(i).getRepresentation()[j][k];
                    result.get(i)[j * 2][k * 2 + 1] = uvParam.getStorage().get(i).getRepresentation()[j][k];
                    result.get(i)[j * 2 + 1][k * 2 + 1] = uvParam.getStorage().get(i).getRepresentation()[j][k];
                }
            }
        }
        return BlockRepositoryImpl.builder()
                .storage(CustomFunctions.fromListOfMtrixesToListOfBlocks(result))
                .build();
    }

    private static PPMImage createImageResult(BlockRepository yValues,
                                              BlockRepository uValues,
                                              BlockRepository vValues){
        var image = PPMImage.PPMImageBuilder.Build();
        image.setWidth(800);
        image.setHeigth(600);
        image.setY(decodeBlock(yValues));
        image.setU(decodeBlock(uValues));
        image.setV(decodeBlock(vValues));
        image.initializeRGB();
        return image;
    }

    public static void decodeImage(BlockRepository yValues,
                                   BlockRepository uValues,
                                   BlockRepository vValues) throws IOException {
        var imageResult = createImageResult(yValues, uValues, vValues);
        recomposeImage(imageResult);
    }

    /**
     * @param blocks For every block in the list I put it in a cretain order to keep its position in the image
     * @return decoded matrix coresponding to the block type introduced
     */
    private static double[][] decodeBlock(BlockRepository blocks){
        double[][] matrix = new double[600][800];
        int line = 0;
        int column = 0;
        for (var block : blocks.getStorage())
        {
            for (int i = 0; i < 8; i++)
                System.arraycopy(block.getRepresentation()[i], 0, matrix[line + i], column, 8);
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

    public static AbstractBlockRepository applyInverseDCTOnBlocks(AbstractBlockRepository abstractBlockRepository){
        var representation = abstractBlockRepository
                .getStorage()
                .stream()
                .map(DecoderBlockService::applyDeQuantization)
                .map(DecoderBlockService::applyInverseDCTOnBlock)
                .map(DecoderBlockService::applyAddition)
                .collect(Collectors.toList());
        return new GBlockRepository(representation);
    }

    private static Block applyInverseDCTOnBlock(Block block){
        var representation = block.getRepresentation();
        var f = new double[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                f[x][y] = ProgramInitializer.CONST * outerSum(representation, x, y);
            }
        }
        return Block.builder()
                .representation(f)
                .build();
    }

    private static Block applyDeQuantization(Block block){
        return CustomFunctions.multiply(block);
    }

    private static Block applyAddition(Block block){
        return CustomFunctions.add(block);
    }

    private static double outerSum(double[][] matrix, int x, int y) {
        double sum = 0.0;
        for (int u = 0; u < 8; u++)
            sum += innerSum(matrix, x, y, u);
        return sum;
    }

    private static double innerSum(double[][] matrix, int x, int y, int u) {
        double sum = 0.0;
        for (int v = 0; v < 8; v++)
            sum += product(matrix[u][v], x, y, u, v);
        return sum;
    }

    private static double product(double matrixValue, int x, int y, int u, int v) {
        var cosU = Math.cos((2 * x + 1) * u * Math.PI / 16);
        var cosV = Math.cos((2 * y + 1) * v * Math.PI / 16);
        return alpha(u) * alpha(v) * matrixValue * cosU * cosV;
    }
}
