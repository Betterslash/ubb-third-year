package model.factory;

import model.Position;
import model.RunningConfiguration;
import model.computations.MatrixElementColumnComputation;
import model.computations.MatrixElementComputation;
import model.computations.MatrixElementRowComputation;

public final class MatrixComputationFactory {
    public static MatrixElementComputation createComputation(Position position){
        return switch (RunningConfiguration.SPLITTING_TYPE){
            case ROW -> new MatrixElementRowComputation(position);
            case COLUMN -> new MatrixElementColumnComputation(position);
            case ELEMENT -> null;
        };
    }
}
