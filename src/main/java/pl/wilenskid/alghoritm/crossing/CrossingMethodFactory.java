package pl.wilenskid.alghoritm.crossing;

import pl.wilenskid.alghoritm.crossing.impl.CrossingMethodEvenly;
import pl.wilenskid.alghoritm.crossing.impl.CrossingMethodMultiplySpot;
import pl.wilenskid.alghoritm.crossing.impl.CrossingMethodOneSpot;
import pl.wilenskid.alghoritm.crossing.impl.CrossingMethodTwoSpot;
import pl.wilenskid.generic.GenericFactory;

public class CrossingMethodFactory extends GenericFactory<CrossingType, CrossingMethod> {

    public CrossingMethodFactory() {
        super();
        this.context.put(CrossingType.ONE_SPOT, new CrossingMethodOneSpot());
        this.context.put(CrossingType.TWO_SPOT, new CrossingMethodTwoSpot());
        this.context.put(CrossingType.MULTIPLY_SPOT, new CrossingMethodMultiplySpot());
        this.context.put(CrossingType.EVENLY, new CrossingMethodEvenly());
    }

}
