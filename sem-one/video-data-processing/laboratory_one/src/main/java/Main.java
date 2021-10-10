import model.BlockService;
import model.PPMImage;
import model.ValueSignature;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        var image = PPMImage
                .PPMImageBuilder
                .Build("D:\\Uni\\sem-one\\video-data-processing\\laboratory_one\\src\\main\\resources\\images\\nt-P3.ppm");
        var blockY = BlockService.divideInEightByEight(image, ValueSignature.Y);
        var blockU = BlockService.scaleEightByEight(image, ValueSignature.U);
        var blockV = BlockService.scaleEightByEight(image, ValueSignature.V);
        blockY.forEach(e -> Arrays.stream(e).forEach(q -> Arrays.stream(q).forEach(System.out::println)));
        blockU.forEach(e -> Arrays.stream(e).forEach(q -> Arrays.stream(q).forEach(System.out::println)));
        blockV.forEach(e -> Arrays.stream(e).forEach(q -> Arrays.stream(q).forEach(System.out::println)));
    }
}
