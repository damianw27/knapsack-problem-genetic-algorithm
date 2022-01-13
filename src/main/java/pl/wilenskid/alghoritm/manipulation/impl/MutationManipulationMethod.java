package pl.wilenskid.alghoritm.manipulation.impl;

import pl.wilenskid.alghoritm.manipulation.ManipulationMethod;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackPair;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MutationManipulationMethod implements ManipulationMethod {

    private final Random rnd;

    public MutationManipulationMethod() {
        this.rnd = new Random();
    }

    @Override
    public List<Knapsack> manipulate(List<Knapsack> knapsacks, double probability) {
        return knapsacks.stream()
                .map(subject -> mutate(subject, probability))
                .collect(Collectors.toList());
    }

    private Knapsack mutate(Knapsack knapsack, double probability) {
        String initialBinaryString = knapsack.getBinaryString();
        StringBuilder finalBinaryString = new StringBuilder();

        for (int i = 0; i < initialBinaryString.length(); i++) {
            double r = (rnd.nextDouble() + (1f - rnd.nextDouble())) / 2f;
            char currentChar = initialBinaryString.charAt(i);


            if (r < probability) {
                currentChar = currentChar == '0' ? '1' : '0';
            }

            finalBinaryString.append(currentChar);
        }

        return new Knapsack(knapsack, finalBinaryString.toString(), new KnapsackPair(knapsack, knapsack));
    }
}
