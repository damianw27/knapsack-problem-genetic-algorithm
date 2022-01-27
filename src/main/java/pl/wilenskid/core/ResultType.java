package pl.wilenskid.core;

public enum ResultType {
    ;
    private final String label;

    ResultType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ResultType{" +
                "label='" + label + '\'' +
                '}';
    }
}
