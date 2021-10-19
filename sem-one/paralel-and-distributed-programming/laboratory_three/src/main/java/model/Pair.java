package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Pair {
    private final MatrixRepresentation firstMatrix;
    private final MatrixRepresentation secondMatrix;
}
