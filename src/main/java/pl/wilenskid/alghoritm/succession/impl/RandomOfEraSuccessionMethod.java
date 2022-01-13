package pl.wilenskid.alghoritm.succession.impl;

import pl.wilenskid.alghoritm.succession.SuccessionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomOfEraSuccessionMethod extends SuccessionMethod {

    @Override
    public List<Knapsack> getFinalPopulation(List<Knapsack> cumulatedPopulation, int size, boolean max) {
        Collections.shuffle(cumulatedPopulation);
        return cumulatedPopulation.stream().limit(size).collect(Collectors.toList());
    }

}
