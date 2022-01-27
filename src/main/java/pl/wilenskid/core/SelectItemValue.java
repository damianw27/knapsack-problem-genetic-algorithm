package pl.wilenskid.core;

public abstract class SelectItemValue<ValueType> {

    private final ValueType valueType;

    public SelectItemValue(ValueType valueType) {
        this.valueType = valueType;
    }

    @Override
    public abstract String toString();
}
