import model.DecoderBlockService;
import model.EncoderBlockService;
import model.PPMImage;
import model.ValueSignature;

import java.io.IOException;
import java.util.Arrays;

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
            blockY.forEach(e -> Arrays.stream(e).forEach(q -> Arrays.stream(q).forEach(System.out::println)));
            blockU.forEach(e -> Arrays.stream(e).forEach(q -> Arrays.stream(q).forEach(System.out::println)));
            blockV.forEach(e -> Arrays.stream(e).forEach(q -> Arrays.stream(q).forEach(System.out::println)));
            DecoderBlockService.decodeImage(blockY, DecoderBlockService.expandUV(blockU), DecoderBlockService.expandUV(blockV));
        }
    }
}
