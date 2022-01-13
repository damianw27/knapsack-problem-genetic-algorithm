package pl.wilenskid.alghoritm.crossing.impl;

import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrossingMethodMultiplySpot extends BaseCrossingMethod {

    public CrossingMethodMultiplySpot() {
        super();
    }

    @Override
    public List<Knapsack> getResult(List<Knapsack> ableToCross,
                                    List<Knapsack> unableToCross,
                                    List<KnapsackPair> knapsackPairs) {
        List<Knapsack> result = new ArrayList<>();

        int bitsInElement = knapsackPairs.size() > 0
                ? knapsackPairs.get(0).getFirst().getBinaryString().length()
                : 0;

        knapsackPairs
                .forEach(pair -> {

                    int indexesCount = rnd.nextInt(bitsInElement);

                    int[] indexes = new int[indexesCount];

                    for (int i = 0; i < indexesCount; i++) {
                        indexes[i] = rnd.nextInt(bitsInElement);
                    }

                    Collections.addAll(result, cross(pair.getFirst(), pair.getSecond(), indexes));

                });

        unableToCross
                .forEach(knapsack -> result.add(new Knapsack(knapsack)));

        return result;
    }

}
