package pl.wilenskid.alghoritm.encoding;

import pl.wilenskid.alghoritm.encoding.impl.BinaryEncodingMethod;
import pl.wilenskid.generic.GenericFactory;

public class EncodingMethodFactory extends GenericFactory<EncodingType, EncodingMethod> {

    public EncodingMethodFactory() {
        super();
        this.context.put(EncodingType.BINARY, new BinaryEncodingMethod());
    }

}
