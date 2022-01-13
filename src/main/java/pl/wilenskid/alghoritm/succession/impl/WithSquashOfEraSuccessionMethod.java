package pl.wilenskid.alghoritm.succession.impl;

import pl.wilenskid.alghoritm.succession.SuccessionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WithSquashOfEraSuccessionMethod extends SuccessionMethod {

    @Override
    public List<Knapsack> getFinalPopulation(List<Knapsack> cumulatedPopulation, int size, boolean max) {
        List<Knapsack> tmpCumulatedPopulation = new ArrayList<>(cumulatedPopulation);
        tmpCumulatedPopulation.sort(Comparator.comparingDouble(Knapsack::calculateAdaptationValue));

        if (max) {
            Collections.reverse(tmpCumulatedPopulation);
        }

        List<Integer> indexesToRemove = new ArrayList<>();

        for (int index = 0; index < tmpCumulatedPopulation.size() - 1; index++) {

            if (((tmpCumulatedPopulation.size() - 1) - indexesToRemove.size()) == 10) {
                break;
            }

            Knapsack current = tmpCumulatedPopulation.get(index);
            Knapsack next = tmpCumulatedPopulation.get(index + 1);

            if (Math.abs(next.calculateAdaptationValue() - current.calculateAdaptationValue()) < 0.5f) {
                indexesToRemove.add(index);
            }

        }

        for (int index : indexesToRemove) {
            if (index > tmpCumulatedPopulation.size() - 1) {
                continue;
            }
            tmpCumulatedPopulation.remove(index);
        }

        return cumulatedPopulation.stream().limit(size).collect(Collectors.toList());
    }

}
