package pl.wilenskid.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ArrayUtil {
    private ArrayUtil() {
    }

    public static int rowToDecimal(Integer[] array) {
        String rowAsString = rowToString(array);
        return Integer.parseInt(rowAsString, 2);
    }

    public static String rowToString(Integer[] array) {
        return Arrays
                .stream(array)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
