package pl.wilenskid.alghoritm.succession;

import pl.wilenskid.alghoritm.AlgorithmConfig;
import pl.wilenskid.alghoritm.AlgorithmsFactories;
import pl.wilenskid.alghoritm.manipulation.ManipulationType;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SuccessionMethod {
    public Map<Integer, List<Knapsack>> succession(List<Knapsack> initialPopulation, AlgorithmConfig algorithmConfig) {
        Map<Integer, List<Knapsack>> evolution = new HashMap<>();
        List<Knapsack> currentPopulation = new ArrayList<>(initialPopulation);

        for (int era = 0; era < algorithmConfig.getErasCount(); era++) {

            currentPopulation = AlgorithmsFactories
                    .getInstance()
                    .getSelectionMethodFactory()
                    .getByType(algorithmConfig.getSelectionType())
                    .select(currentPopulation, algorithmConfig.isMax());

            List<Knapsack> mutatedPopulation = AlgorithmsFactories
                    .getInstance()
                    .getManipulationMethodFactory()
                    .getByType(ManipulationType.MUTATION)
                    .manipulate(currentPopulation, algorithmConfig.getMutationProbability());

            List<Knapsack> invertedPopulation = AlgorithmsFactories
                    .getInstance()
                    .getManipulationMethodFactory()
                    .getByType(ManipulationType.INVERSE)
                    .manipulate(currentPopulation, algorithmConfig.getInversionProbability());

            List<Knapsack> crossedPopulation = AlgorithmsFactories
                    .getInstance()
                    .getCrossingMethodFactory()
                    .getByType(algorithmConfig.getCrossingType())
                    .cross(currentPopulation, algorithmConfig.getCrossingProbability());

            List<Knapsack> cumulatedPopulation = new ArrayList<>(currentPopulation);
            cumulatedPopulation.addAll(mutatedPopulation);
            cumulatedPopulation.addAll(invertedPopulation);
            cumulatedPopulation.addAll(crossedPopulation);

            List<Knapsack> distinctPopulation = cumulatedPopulation
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());

            List<Knapsack> finalPopulation = getFinalPopulation(
                    distinctPopulation,
                    initialPopulation.size(),
                    algorithmConfig.isMax()
            );

            evolution.put(era, finalPopulation);
        }

        return evolution;
    }

    protected abstract List<Knapsack> getFinalPopulation(List<Knapsack> cumulatedPopulation, int size, boolean max);


}
