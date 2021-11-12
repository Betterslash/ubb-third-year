package service;

import model.PPMImage;
import model.ValueSignature;
import model.block.Block;
import repository.AbstractBlockRepository;
import repository.BlockRepository;
import repository.BlockRepositoryImpl;
import repository.GBlockRepository;
import utils.CustomFunctions;

import java.util.ArrayList;

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

    public static BlockRepository divideInEightByEight(PPMImage image, ValueSignature signature){
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
        return BlockRepositoryImpl.builder()
                .storage(CustomFunctions.fromListOfMtrixesToListOfBlocks(results))
                .build();
    }

    public static BlockRepository scaleEightByEight(PPMImage image, ValueSignature signature){
        var results = new ArrayList<double[][]>();
        var toBeScaled = divideInEightByEight(image, signature);
        for (var value: toBeScaled.getStorage()) {
            var elements = new ArrayList<Double>();
            var result = new double[4][4];
            for(int p = 0; p < 8; p+=2) {
                for (int t = 0; t < 8; t+=2) {
                    var sumForAverage = 0d;
                    sumForAverage += value.getRepresentation()[p][t];
                    sumForAverage += value.getRepresentation()[p][t+1];
                    sumForAverage += value.getRepresentation()[p+1][t];
                    sumForAverage += value.getRepresentation()[p+1][t+1];
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
        return BlockRepositoryImpl.builder()
                .storage(CustomFunctions.fromListOfMtrixesToListOfBlocks(results))
                .build();
    }

    public static AbstractBlockRepository applyForwardDCTOnBlocks(BlockRepository blocks){
        var result =  new GBlockRepository(new ArrayList<>());
        blocks.getStorage()
                .stream()
                .map(EncoderBlockService::applySubtraction)
                .map(EncoderBlockService::applyForwardDCTOnBlock)
                .map(EncoderBlockService::applyQuantization)
                .forEach(result::addToStorage);
        return result;
    }

    private static Block applyForwardDCTOnBlock(Block block){
        var G = new double[8][8];
        for (int u = 0; u < 8; u++) {
            for (int v = 0; v < 8; v++) {
                G[u][v] = 0.25 * CustomFunctions.alpha(u) * CustomFunctions.alpha(v) * CustomFunctions.outerSum(block.getRepresentation(), u, v);
            }
        }
        return Block.builder()
                .representation(G)
                .build();
    }

    private static Block applySubtraction(Block block) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                block.getRepresentation()[i][j] -= 128;
            }
        }
        return block;
    }

    private static Block applyQuantization(Block block) {
        return CustomFunctions.divide(block);
    }
}
