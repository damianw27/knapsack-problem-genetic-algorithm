package pl.wilenskid.alghoritm.range;

import pl.wilenskid.alghoritm.range.impl.BaseRangeMethod;
import pl.wilenskid.generic.GenericFactory;

public class RangeMethodFactory extends GenericFactory<RangeType, RangeMethod> {

    public RangeMethodFactory() {
        super();
        this.context.put(RangeType.BASE, new BaseRangeMethod());
    }

}
