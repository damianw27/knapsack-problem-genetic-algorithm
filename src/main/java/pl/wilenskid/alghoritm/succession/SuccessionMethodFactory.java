package pl.wilenskid.alghoritm.succession;

import pl.wilenskid.alghoritm.succession.impl.BestOfEraSuccessionMethod;
import pl.wilenskid.alghoritm.succession.impl.RandomOfEraSuccessionMethod;
import pl.wilenskid.alghoritm.succession.impl.TrivialSuccessionMethod;
import pl.wilenskid.alghoritm.succession.impl.WithSquashOfEraSuccessionMethod;
import pl.wilenskid.generic.GenericFactory;

public class SuccessionMethodFactory extends GenericFactory<SuccessionType, SuccessionMethod> {

    public SuccessionMethodFactory() {
        super();
        this.context.put(SuccessionType.BEST_OF_ERA, new BestOfEraSuccessionMethod());
        this.context.put(SuccessionType.RANDOM_OF_ERA, new RandomOfEraSuccessionMethod());
        this.context.put(SuccessionType.TRIVIAL, new TrivialSuccessionMethod());
        this.context.put(SuccessionType.WITH_SQUASH_OF_ERA, new WithSquashOfEraSuccessionMethod());
    }

}
