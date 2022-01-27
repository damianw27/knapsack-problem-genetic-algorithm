package pl.wilenskid.core;

public enum DisplayType {
    VALUE("Value"),
    WEIGHT("Weight"),
    PRICE("Price");

    private final String label;

    DisplayType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
