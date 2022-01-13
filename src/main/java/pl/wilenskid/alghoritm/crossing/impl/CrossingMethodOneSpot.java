package pl.wilenskid.alghoritm.crossing.impl;

import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrossingMethodOneSpot extends BaseCrossingMethod {

    public CrossingMethodOneSpot() {
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
                .forEach(pair -> Collections.addAll(result, cross(pair.getFirst(), pair.getSecond(), rnd.nextInt(bitsInElement))));

        unableToCross
                .forEach(knapsack -> result.add(new Knapsack(knapsack)));

        return result;
    }

}
