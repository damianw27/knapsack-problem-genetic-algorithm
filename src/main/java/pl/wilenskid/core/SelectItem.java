package pl.wilenskid.core;

import lombok.Getter;

@Getter
public class SelectItem<ValueType> {

    private final String label;
    private final ValueType value;

    public SelectItem(String label, ValueType value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public final String toString() {
        return label;
    }
}
