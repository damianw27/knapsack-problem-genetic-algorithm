package pl.wilenskid.alghoritm.crossing.impl;

import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrossingMethodEvenly extends BaseCrossingMethod {

    public CrossingMethodEvenly() {
        super();
    }

    @Override
    public List<Knapsack> getResult(List<Knapsack> ableToCross,
                                    List<Knapsack> unableToCross,
                                    List<KnapsackPair> knapsackPairs) {
        List<Knapsack> result = new ArrayList<>();

        knapsackPairs
                .stream()
                .map(this::evenly)
                .forEach(result::addAll);

        unableToCross
                .forEach(knapsack -> result.add(new Knapsack(knapsack)));

        return result;
    }

    private List<Knapsack> evenly(KnapsackPair pair) {
        Knapsack parent1 = pair.getFirst();
        Knapsack parent2 = pair.getSecond();

        StringBuilder firstResultBinaryString = new StringBuilder();
        StringBuilder secondResultBinaryString = new StringBuilder();

        String firstSubjectBinaryString = parent1.getBinaryString();
        String secondSubjectBinaryString = parent2.getBinaryString();

        for (int index = 0; index < firstSubjectBinaryString.length(); index++) {

            boolean shouldChange = rnd.nextBoolean();

            char firstResultChar = shouldChange
                    ? firstSubjectBinaryString.charAt(index)
                    : secondSubjectBinaryString.charAt(index);

            char secondResultChar = shouldChange
                    ? secondSubjectBinaryString.charAt(index)
                    : firstSubjectBinaryString.charAt(index);

            firstResultBinaryString.append(firstResultChar);
            secondResultBinaryString.append(secondResultChar);

        }

        return Arrays.asList(
                new Knapsack(parent1, firstResultBinaryString.toString(), pair),
                new Knapsack(parent2, secondResultBinaryString.toString(), pair)
        );
    }

}
