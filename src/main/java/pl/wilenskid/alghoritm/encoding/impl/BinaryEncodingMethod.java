package pl.wilenskid.alghoritm.encoding.impl;

import pl.wilenskid.alghoritm.encoding.EncodingMethod;

public class BinaryEncodingMethod implements EncodingMethod {
    @Override
    public int encode(int d, double a, double b) {
        int m = 0;
        double boundValue = calculateBoundValue(d, a, b);

        while (condition(m, boundValue)) {
            m++;
        }

        return m;
    }

    private double calculateBoundValue(int d, double a, double b) {
        if (a == b) {
            return 0f;
        }

        return (b - a) * Math.pow(10, d) + 1f;
    }

    private boolean condition(int m, double boundValue) {
        return Math.pow(2, m - 1) <= boundValue
                && boundValue >= Math.pow(2, m);
    }
}
