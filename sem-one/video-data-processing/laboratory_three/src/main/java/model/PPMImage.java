package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.ImageReader;

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
            width = Integer.parseInt(sizes[0]);
            heigth = Integer.parseInt(sizes[1]);
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
                u[i][j] = 0.492 * (b[i][j] - y[i][j]);
                v[i][j] = 0.887 * (r[i][j] - y[i][j]);
            }
        }
    }

    public void initializeRGB(){
        r = new int[heigth][width];
        g = new int[heigth][width];
        b = new int[heigth][width];
        for (int j = 0; j < heigth; j++) {
            for (int k = 0; k < width; k++) {
                r[j][k] = (int)(y[j][k] + 1.140 * v[j][k]);
                g[j][k] = (int)(y[j][k] - 0.395 * u[j][k] - 0.581 * v[j][k]);
                b[j][k] = (int)(y[j][k] + 2.032 * u[j][k]);
                if(r[j][k] > 255){
                    r[j][k] = 255;
                }
                if(g[j][k] > 255){
                    g[j][k] = 255;
                }
                if(b[j][k] > 255){
                    b[j][k] = 255;
                }
                if(r[j][k] < 0){
                    r[j][k] = 0;
                }
                if(g[j][k] < 0){
                    g[j][k] = 0;
                }
                if(b[j][k] < 0){
                    b[j][k] = 0;
                }
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
