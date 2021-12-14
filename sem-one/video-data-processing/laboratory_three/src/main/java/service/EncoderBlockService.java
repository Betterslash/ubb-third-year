package service;

import model.PPMImage;
import model.ValueSignature;
import model.block.Block;
import repository.AbstractBlockRepository;
import repository.BlockRepository;
import repository.BlockRepositoryImpl;
import repository.GBlockRepository;
import utils.CustomFunctions;
import utils.ProgramInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        var result = blocks
                .getStorage()
                .stream()
                .map(EncoderBlockService::applySubtraction)
                .map(EncoderBlockService::applyForwardDCTOnBlock)
                .map(EncoderBlockService::applyQuantization)
                .collect(Collectors.toList());
        return new GBlockRepository(result);
    }

    private static Block applyForwardDCTOnBlock(Block block){
        var representation = block.getRepresentation();
        var G = new double[8][8];
        for (int u = 0; u < 8; u++) {
            for (int v = 0; v < 8; v++) {
                G[u][v] = ProgramInitializer.CONST * CustomFunctions.alpha(u) * CustomFunctions.alpha(v) * outerSum(representation, u, v);
            }
        }
        return Block.builder()
                .representation(G)
                .build();
    }

    private static Block applySubtraction(Block block) {
        return CustomFunctions.subtract(block);
    }

    private static Block applyQuantization(Block block) {
        return CustomFunctions.divide(block);
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

    public static List<List<Integer>> entropyEncoding(Block block){
        var entropy = new ArrayList<List<Integer>>();
        var entropyList = CustomFunctions.applyZigZagTransform(block);
        entropy.add(Arrays.asList(getPlaceInTable(entropyList.get(0)), entropyList.get(0).intValue()));
        for (int i = 1; i <=63; i++) {
            var RUN_LENGTH = 0;
            while(entropyList.get(i) == 0){
                RUN_LENGTH++;
                i++;
                if(i == 64){
                    break;
                }
            }
            if (i == 64 )
                entropy.add(Arrays.asList(0, 0));
            else
            {
                entropy.add(Arrays.asList(RUN_LENGTH, getPlaceInTable(entropyList.get(i)), entropyList.get(i).intValue()));
            }
        }
        return entropy;
    }

    private static Integer getPlaceInTable(Double aDouble) {
        var result = 1;
        for (var e: ProgramInitializer.coefficientTable.entrySet()) {
            for (int i = 0; i < e.getValue().size() - 1; i++) {
                if((e.getValue().get(i) <= aDouble) && (aDouble <= e.getValue().get(i + 1))){
                    return e.getKey();
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> getDCList(List<Block> yBlocks, List<Block> uBlocks, List<Block> vBlocks){
        var size =  yBlocks.size();
        var result = new ArrayList<List<Integer>>();
        for (int i = 0; i < size; i++) {
            getEntropiesListForBlocks(yBlocks.get(i), uBlocks.get(i), vBlocks.get(i), result);
        }
        return result;
    }

    private static void getEntropiesListForBlocks(Block yBlock, Block uBlock, Block vBlock, List<List<Integer>> result){
        var yArray = entropyEncoding(yBlock);
        var uArray = entropyEncoding(uBlock);
        var vArray = entropyEncoding(vBlock);

        result.addAll(yArray);
        result.addAll(uArray);
        result.addAll(vArray);
    }
}
