package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
public class PPMImage {
    private String fileName;
    private int heigth = 600;
    private int width = 800;
    private String fromat;
    private int maxValue;

    private int[][] r;
    private int[][] g;
    private int[][] b;

    private double[][] y;
    private double[][] u;
    private double[][] v;

    private PPMImage(String path){
        this.fileName = path;
        this.readImage();
        this.initializeYUV();
    }

    private void readImage() {
        var internalRepresentation = ImageReader.initializeImage(fileName);
        try{
            fromat = internalRepresentation.readLine();
            internalRepresentation.readLine();
            var sizes = internalRepresentation.readLine().split(" ");
            heigth = Integer.parseInt(sizes[0]);
            width = Integer.parseInt(sizes[1]);
            maxValue = Integer.parseInt(internalRepresentation.readLine());
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        r = new int[heigth][width];
        g = new int[heigth][width];
        b = new int[heigth][width];

        y = new double[heigth][width];
        u = new double[heigth][width];
        v = new double[heigth][width];

        var line = 0;
        var column = 0;
        var readed = "";
        try {
            while ((readed = internalRepresentation.readLine()) != null && line != heigth) {
                if (column == width) {
                    column = 0;
                    line++;
                }
                r[line][column] = Integer.parseInt(readed);
                g[line][column] = Integer.parseInt(internalRepresentation.readLine());
                b[line][column] = Integer.parseInt(internalRepresentation.readLine());
                column++;
            }
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void initializeYUV(){
        y = new double[heigth][width];
        u = new double[heigth][width];
        v = new double[heigth][width];
        for(int i = 0; i < heigth; i++){
            for(int j = 0; j < width; j ++){
                y[i][j] = 0.299 * r[i][j] + 0.587 * g[i][j] + 0.114 * b[i][j];
                u[i][j] = 128 - 0.168736 * r[i][j] - 0.331264 * g[i][j] + 0.5 * b[i][j];
                v[i][j] = 128 + 0.5 * r[i][j] - 0.418688 * g[i][j] - 0.081312 * b[i][j];
            }
        }
    }

    public void initializeRGB(){
        r = new int[heigth][width];
        g = new int[heigth][width];
        b = new int[heigth][width];
        for (int j = 0; j < heigth; j++) {
            for (int k = 0; k < width; k++) {
                r[j][k] = (int)(y[j][k] + 1.402 * (u[j][k] -128));
                g[j][k] = (int)(y[j][k] - 0.344136 *(u[j][k] - 128) - 0.714136 * (v[j][k] -128));
                b[j][k] = (int)(y[j][k] + 1.7790 * (u[j][k] - 128));
            }
        }
    }

    public static class PPMImageBuilder{
        public static PPMImage Build(String path){
            return new PPMImage(path);
        }
        public static PPMImage Build(){
            return new PPMImage();
        }
    }
}
