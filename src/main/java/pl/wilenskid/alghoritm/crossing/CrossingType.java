package pl.wilenskid.alghoritm.crossing;

public enum CrossingType {
    MULTIPLY_SPOT("Multiple spot"),
    ONE_SPOT("One spot"),
    TWO_SPOT("Two spot"),
    EVENLY("Evenly");

    private final String label;

    CrossingType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
