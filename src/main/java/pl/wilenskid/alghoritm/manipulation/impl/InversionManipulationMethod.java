package pl.wilenskid.alghoritm.manipulation.impl;

import pl.wilenskid.alghoritm.manipulation.ManipulationMethod;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackPair;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InversionManipulationMethod implements ManipulationMethod {

    private final Random rnd;

    public InversionManipulationMethod() {
        this.rnd = new Random();
    }

    @Override
    public List<Knapsack> manipulate(List<Knapsack> knapsacks, double probability) {
        return knapsacks
                .stream()
                .map(subject -> inverse(subject, probability))
                .collect(Collectors.toList());
    }

    private Knapsack inverse(Knapsack knapsack, double probability) {
        String initialBinaryString = knapsack.getBinaryString();
        String finalBinaryString = initialBinaryString;
        double r = (rnd.nextDouble() + (1f - rnd.nextDouble())) / 2f;

        if (r > probability) {
            return knapsack;
        }

        int indexStart = rnd.nextInt(initialBinaryString.length());
        int indexEnd = rnd.nextInt(initialBinaryString.length() - indexStart) + indexStart;
        String binarySubString = initialBinaryString.substring(indexStart, indexEnd);
        String invertedBinarySubString = new StringBuilder(binarySubString).reverse().toString();

        finalBinaryString = finalBinaryString.replace(binarySubString, invertedBinarySubString);

        return new Knapsack(knapsack, finalBinaryString, new KnapsackPair(knapsack, knapsack));
    }
}
