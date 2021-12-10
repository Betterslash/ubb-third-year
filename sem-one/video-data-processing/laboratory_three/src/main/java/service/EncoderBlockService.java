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

    public static List<Integer> entropy = new ArrayList<>();

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

    public static void entropyEncoding(Block block){
        var entropyList = CustomFunctions.applyZigZagTransform(block.getRepresentation(), 8, 8);
        var DCsize = entropyList.size();
        entropy.addAll(List.of(DCsize));
        for (int i = 0; i < 64; i++) {
            var cnt = 0;
            while(entropyList.get(i) == 0){
                cnt++;
                i++;
                if(i == 64){
                    break;
                }
            }
            if (i == 64 )
                entropy.addAll(Arrays.asList(0, 0));
            else
            {
                entropy.addAll(Arrays.asList(cnt, getPlaceInTable(entropyList.get(i)), entropyList.get(i).intValue()));
            }
        }
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
}
