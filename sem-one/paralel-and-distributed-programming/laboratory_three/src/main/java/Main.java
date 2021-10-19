import model.ComputeElement;
import model.MatrixRepresentation;

public class Main {
    public static void main(String[] args) throws Exception {
        var matrixes = MatrixRepresentation.getPairInstances();

        System.out.println(matrixes.getFirstMatrix().toString());
        System.out.println(matrixes.getSecondMatrix().toString());

        var height = matrixes
                .getFirstMatrix()
                .getHeight();
        var width = matrixes
                .getSecondMatrix()
                .getWidth();

        var result = new Double[height][height];


        var repr = MatrixRepresentation.valueOf(result, height, height);
        System.out.println(repr);
    }
}
