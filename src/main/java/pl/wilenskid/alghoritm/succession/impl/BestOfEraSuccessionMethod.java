package pl.wilenskid.alghoritm.succession.impl;

import pl.wilenskid.alghoritm.succession.SuccessionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BestOfEraSuccessionMethod extends SuccessionMethod {

    @Override
    public List<Knapsack> getFinalPopulation(List<Knapsack> cumulatedPopulation, int size, boolean max) {
        List<Knapsack> population = cumulatedPopulation.stream()
                .sorted(Comparator.comparingDouble(Knapsack::calculateAdaptationValue))
                .distinct()
                .collect(Collectors.toList());

        if (max) {
            Collections.reverse(population);
        }

        return population.stream().limit(size).collect(Collectors.toList());
    }

}
