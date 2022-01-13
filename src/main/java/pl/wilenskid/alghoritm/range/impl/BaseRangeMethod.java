package pl.wilenskid.alghoritm.range.impl;

import pl.wilenskid.alghoritm.range.RangeMethod;

public class BaseRangeMethod implements RangeMethod {
    @Override
    public double calculate(int value, double a, double b, int m) {
        return (b - a) * value / (Math.pow(2, m) - 1) + a;
    }
}
