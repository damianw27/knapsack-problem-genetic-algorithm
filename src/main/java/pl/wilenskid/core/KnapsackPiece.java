package pl.wilenskid.core;

import lombok.Value;

@Value
public class KnapsackPiece {
    int xPrim;
    double a;
    double b;
    int m;

    public double getX() {
        return a + ((b - a) * xPrim) / (Math.pow(2, m) - 1);
    }
}
