package pl.wilenskid.alghoritm.succession.impl;

import pl.wilenskid.alghoritm.AlgorithmConfig;
import pl.wilenskid.alghoritm.AlgorithmsFactories;
import pl.wilenskid.alghoritm.manipulation.ManipulationType;
import pl.wilenskid.alghoritm.succession.SuccessionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrivialSuccessionMethod extends SuccessionMethod {

    @Override
    public Map<Integer, List<Knapsack>> succession(List<Knapsack> initialPopulation, AlgorithmConfig algorithmConfig) {
        Map<Integer, List<Knapsack>> evolution = new HashMap<>();
        List<Knapsack> currentPopulation = new ArrayList<>(initialPopulation);

        for (int era = 0; era < algorithmConfig.getErasCount(); era++) {

            currentPopulation = AlgorithmsFactories
                    .getInstance()
                    .getSelectionMethodFactory()
                    .getByType(algorithmConfig.getSelectionType())
                    .select(currentPopulation, algorithmConfig.isMax());

            currentPopulation = AlgorithmsFactories
                    .getInstance()
                    .getManipulationMethodFactory()
                    .getByType(ManipulationType.MUTATION)
                    .manipulate(currentPopulation, algorithmConfig.getMutationProbability());

            currentPopulation = AlgorithmsFactories
                    .getInstance()
                    .getManipulationMethodFactory()
                    .getByType(ManipulationType.INVERSE)
                    .manipulate(currentPopulation, algorithmConfig.getInversionProbability());

            currentPopulation = AlgorithmsFactories
                    .getInstance()
                    .getCrossingMethodFactory()
                    .getByType(algorithmConfig.getCrossingType())
                    .cross(currentPopulation, algorithmConfig.getCrossingProbability());

            evolution.put(era, currentPopulation);

        }

        return evolution;
    }

    @Override
    public List<Knapsack> getFinalPopulation(List<Knapsack> cumulatedPopulation, int size, boolean max) {
        return null;
    }

}
