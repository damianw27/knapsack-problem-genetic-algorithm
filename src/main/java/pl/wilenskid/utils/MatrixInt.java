package pl.wilenskid.utils;

import lombok.Data;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Data
public class MatrixInt {
    private final Integer[][] matrix;
    private final int sizeM;
    private final int sizeN;

    public MatrixInt(int m, int n) {
        this.matrix = new Integer[m][n];
        this.sizeM = m;
        this.sizeN = n;
    }

    public static MatrixInt randomBinary(int m, int n) {
        MatrixInt matrixInt = new MatrixInt(m, n);
        Random random = new Random();

        for (int rowIndex = 0; rowIndex < m; rowIndex++) {
            for (int cellIndex = 0; cellIndex < n; cellIndex++) {
                int randomBinaryValue = random.nextBoolean() ? 1 : 0;
                matrixInt.setValue(rowIndex, cellIndex, randomBinaryValue);
            }
        }

        return matrixInt;
    }

    public void setValue(int x, int y, int value) {
        if (isPosNotInMatrix(x, y)) {
            return;
        }

        matrix[x][y] = value;
    }

    public Optional<Integer> getValue(int x, int y) {
        if (isPosNotInMatrix(x, y)) {
            return Optional.empty();
        }

        return Optional.ofNullable(matrix[x][y]);
    }

    public Integer[] getRowAt(int index) {
        if (index >= matrix.length || index < 0) {
            return null;
        }

        return matrix[index];
    }

    private boolean isPosNotInMatrix(int x, int y) {
        return x < 0
                || x >= matrix.length
                || y < 0
                || y >= matrix[0].length;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix);
    }
}
