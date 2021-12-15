import model.PPMImage;
import model.ValueSignature;
import model.block.Block;
import repository.BlockRepository;
import service.DecoderBlockService;
import service.EncoderBlockService;
import utils.CustomFunctions;
import utils.ProgramInitializer;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
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

            DecoderBlockService.decodeImage(decodedY, decoedeU, decodedV);
        }
    }
}
