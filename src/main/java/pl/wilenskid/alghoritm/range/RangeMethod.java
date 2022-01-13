package pl.wilenskid.alghoritm.range;

import pl.wilenskid.alghoritm.range.impl.BaseRangeMethod;

public interface RangeMethod {
    double calculate(int value, double a, double b, int m);

    static RangeMethod getByType(RangeType rangeType) {
        switch (rangeType) {
            default:
            case BASE:
                return new BaseRangeMethod();
        }
    }
}
