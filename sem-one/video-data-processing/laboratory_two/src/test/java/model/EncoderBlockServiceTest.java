package model;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class EncoderBlockServiceTest {

    @Test
    void scaleEightByEight() {
        var testMatrix = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                testMatrix[i][j] = j;
            }
        }
        var sumForAverage = 0;
            var result = new int[4][4];
            for(int p = 0; p < 8; p+=2) {
                for (int t = 0; t < 8; t += 2) {
                    sumForAverage = 0;
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            sumForAverage += testMatrix[p + i][t + j];
                        }
                    }
                    result[p/2][t/2] = sumForAverage / 4;
                }
            }
            var counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(counter, result[i][j]);
                counter += 2;
            }
            counter = 0;
        }
    }
}