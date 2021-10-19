package model;

public class ComputeElement extends MatrixOperation{

    public ComputeElement(int line, int column, Pair matrixes) {
        super(line, column, matrixes);
    }

    public Runnable execution(){
        return () ->
        {
            var firstArray = matrixes
                    .getFirstMatrix()
                    .getLine(line);
            var secondArray = matrixes
                    .getSecondMatrix()
                    .getColumn(column);
            var result = 0d;
            var width = matrixes.getFirstMatrix().getWidth();
            for (int i = 0; i < width; i++) {
                result += firstArray[i] * secondArray[i];
            }
        };
    }
}
