package model;

import model.block.Block;
import org.junit.jupiter.api.Test;
import repository.BlockRepository;
import service.DecoderBlockService;
import service.EncoderBlockService;
import utils.CustomFunctions;
import utils.ProgramInitializer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncoderBlockServiceTest {

    @Test
    void testAll(){
        ProgramInitializer.initializeProgram();
        if(ProgramInitializer.IS_INITIALIZED){
            var image = PPMImage
                    .PPMImageBuilder
                    .Build(ProgramInitializer.IMAGES_FOLDER_PATH + ProgramInitializer.TEST_IMAGE_PATH);
            var blockY = EncoderBlockService.divideInEightByEight(image, ValueSignature.Y);
            var blockU = EncoderBlockService.scaleEightByEight(image, ValueSignature.U);
            var blockV = EncoderBlockService.scaleEightByEight(image, ValueSignature.V);

            var expandedU = DecoderBlockService.expandUV(blockU);
            var expandedV = DecoderBlockService.expandUV(blockV);

            var yGBlockRepository = EncoderBlockService.applyForwardDCTOnBlocks(blockY);
            var uGBlockRepository = EncoderBlockService.applyForwardDCTOnBlocks(expandedU);
            var vGBlockRepository = EncoderBlockService.applyForwardDCTOnBlocks(expandedV);

            var bytesList = EncoderBlockService.getDCList(yGBlockRepository.getStorage(), uGBlockRepository.getStorage(), vGBlockRepository.getStorage());
            var blockRepositories = DecoderBlockService.decodeEntropies(bytesList);

            var decodedY = DecoderBlockService.applyInverseDCTOnBlocks((BlockRepository) blockRepositories.get(0));
            var decoedeU = DecoderBlockService.applyInverseDCTOnBlocks((BlockRepository) blockRepositories.get(1));
            var decodedV = DecoderBlockService.applyInverseDCTOnBlocks((BlockRepository) blockRepositories.get(2));

            try {
                DecoderBlockService.decodeImage(decodedY, decoedeU, decodedV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testScaleEightByEight() {
        var testMatrix = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                testMatrix[i][j] = j;
            }
        }
        var sumForAverage = 0;
            var result = new int[4][4];
            for(int p = 0; p < 8; p+=2) {
                for (int t = 0; t < 8; t += 2) {
                    sumForAverage = 0;
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            sumForAverage += testMatrix[p + i][t + j];
                        }
                    }
                    result[p/2][t/2] = sumForAverage / 4;
                }
            }
            var counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(counter, result[i][j]);
                counter += 2;
            }
            counter = 0;
        }
    }

    @Test
    void testEntropy(){
        var mockBlock = new double[][]{
                {150, 80, 20, 4, 1, 0, 0, 0},
                {92, 75, 18, 3, 1, 0, 0, 0},
                {26, 19, 13, 2, 1, 0, 0, 0},
                {3, 2, 2, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println(CustomFunctions.applyZigZagTransform(Block.builder()
                .representation(mockBlock)
                .build()));
        System.out.println(ProgramInitializer.coefficientTable);
        var entropy = EncoderBlockService.entropyEncoding(Block.builder().representation(mockBlock).build());
        System.out.println(entropy);
        var matr = DecoderBlockService.applyEntropyDecodingOnBlock(entropy);
        System.out.println(matr);
    }

    @Test
    void test(){
        testEntropy();
        testScaleEightByEight();
        testAll();
    }
}