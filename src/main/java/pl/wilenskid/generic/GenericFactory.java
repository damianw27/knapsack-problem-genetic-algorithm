package pl.wilenskid.generic;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericFactory<Type, BaseClass> {

    protected final Map<Type, BaseClass> context;

    public GenericFactory() {
        this.context = new HashMap<>();
    }

    public BaseClass getByType(Type type) {
        return context.get(type);
    }

}
