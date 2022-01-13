package pl.wilenskid.alghoritm.manipulation;

import pl.wilenskid.alghoritm.manipulation.impl.InversionManipulationMethod;
import pl.wilenskid.alghoritm.manipulation.impl.MutationManipulationMethod;
import pl.wilenskid.generic.GenericFactory;

public class ManipulationMethodFactory extends GenericFactory<ManipulationType, ManipulationMethod> {

    public ManipulationMethodFactory() {
        super();
        this.context.put(ManipulationType.INVERSE, new InversionManipulationMethod());
        this.context.put(ManipulationType.MUTATION, new MutationManipulationMethod());
    }

}
