package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RGBPair {
    public int R;
    public int G;
    public int B;

    @Override
    public String toString() {
        return R + System.lineSeparator() +
               G + System.lineSeparator() +
               B + System.lineSeparator();
    }
}
