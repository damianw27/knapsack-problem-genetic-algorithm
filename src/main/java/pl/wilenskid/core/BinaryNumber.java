package pl.wilenskid.core;

public class BinaryNumber {

    public static final int PRECISION = 3;

    private final double decimalValue;
    private final String evenBinaryString;
    private final String restBinaryString;

    public BinaryNumber(double decimalValue) {
        if (!validateNumber(decimalValue)) {
            throw new IllegalArgumentException();
        }

        this.decimalValue = decimalValue;
        this.evenBinaryString = evenToBinarySpring();
        this.restBinaryString = restToBinaryString();
    }

    public BinaryNumber(String binaryString) {
        if (!binaryString.contains("\\.")) {
            this.decimalValue = Integer.parseInt(binaryString, 2);
            this.evenBinaryString = binaryString;
            this.restBinaryString = "";
        } else {
            String[] parts = binaryString.split("\\.");
            int evenPart = Integer.parseInt(parts[0], 2);
            double restPart = Integer.parseInt(parts[1], 2) * 1000d;
            this.decimalValue = evenPart + restPart;
            this.evenBinaryString = parts[0];
            this.restBinaryString = parts[1];
        }
    }

    public void recalculate(String binaryString) {

    }

    public double getDecimalValue() {
        return decimalValue;
    }

    public String getBinaryWithSeparatedRestString() {
        return evenBinaryString + "." + restBinaryString;
    }

    public String getBinaryString() {
        return evenBinaryString + restBinaryString;
    }

    private String evenToBinarySpring() {
        return Integer.toBinaryString((int) decimalValue);
    }

    private String restToBinaryString() {
        return Integer.toBinaryString((int) ((decimalValue - (int) decimalValue) * 1000));
    }

    private boolean validateNumber(double value) {
        String valueString = Double.toString(value);

        if (!valueString.contains("\\.")) {
            return true;
        }

        String restPart = valueString.split("\\.")[1];

        return restPart.length() <= PRECISION;
    }

}
