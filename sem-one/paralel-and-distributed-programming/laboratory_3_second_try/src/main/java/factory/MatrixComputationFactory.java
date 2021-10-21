package factory;

import utils.Position;
import utils.RunningConfiguration;
import computations.MatrixElementColumnComputation;
import computations.MatrixElementComputation;
import computations.MatrixElementKelementComputation;
import computations.MatrixElementRowComputation;

public final class MatrixComputationFactory {
    public static MatrixElementComputation createComputation(Position position){
        return switch (RunningConfiguration.SPLITTING_TYPE){
            case ROW -> new MatrixElementRowComputation(position);
            case COLUMN -> new MatrixElementColumnComputation(position);
            case ELEMENT -> new MatrixElementKelementComputation(position);
        };
    }
}
