package pl.wilenskid.alghoritm.succession;

public enum SuccessionType {
    TRIVIAL("Trivial"),
    BEST_OF_ERA("Best of era"),
    RANDOM_OF_ERA("Random of era"),
    WITH_SQUASH_OF_ERA("With squash of era");

    private final String label;

    SuccessionType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
