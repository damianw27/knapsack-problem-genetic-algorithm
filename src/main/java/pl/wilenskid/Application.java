package pl.wilenskid;

import pl.wilenskid.alghoritm.AlgorithmConfig;
import pl.wilenskid.alghoritm.AlgorithmsFactories;
import pl.wilenskid.alghoritm.crossing.CrossingType;
import pl.wilenskid.alghoritm.selection.SelectionType;
import pl.wilenskid.alghoritm.succession.SuccessionType;
import pl.wilenskid.core.Item;
import pl.wilenskid.core.ItemsSettings;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.RepairKnapsack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Application {
    private static final int ITEMS_COUNT = 14;
    private static final double KNAPSACK_CAPACITY = 100;

    public static void main(String[] args) {
        ItemsSettings itemsSettings = new ItemsSettings(10, 10);
        Item[] items = generateItems(itemsSettings);

        List<Knapsack> initialKnapsacksPopulation = new ArrayList<>();

        for (int j = 0; j < 100; j++) {
            pl.wilenskid.core.Knapsack knapsack = new pl.wilenskid.core.Knapsack(items, KNAPSACK_CAPACITY, null);
            initialKnapsacksPopulation.add(knapsack);
        }

        AlgorithmConfig algorithmConfig = AlgorithmConfig
                .builder()
                .successionType(SuccessionType.RANDOM_OF_ERA)
                .selectionType(SelectionType.TOURNAMENT_METHOD)
                .crossingType(CrossingType.TWO_SPOT)
                .inversionProbability(0.8)
                .mutationProbability(0.8)
                .crossingProbability(0.8)
                .erasCount(100)
                .isMax(true)
                .build();

        Map<Integer, List<Knapsack>> evolution = AlgorithmsFactories
                .getInstance()
                .getSuccessionMethodFactory()
                .getByType(algorithmConfig.getSuccessionType())
                .succession(initialKnapsacksPopulation, algorithmConfig);

        Map<Integer, Double> data = new HashMap<>();

        double max = Double.MIN_VALUE;

        for (int eraIndex : evolution.keySet()) {
            List<Knapsack> subjects = evolution.get(eraIndex);

            double meanAdaptationValue = subjects
                    .parallelStream()
//                    .map(RepairKnapsack::repair)
                    .map(Knapsack::calculateAdaptationValue)
                    .reduce(0d, Double::sum);

            if (max < meanAdaptationValue) {
                max = meanAdaptationValue;
            }

            data.put(eraIndex, meanAdaptationValue);
        }
    }

    private static Item[] generateItems(ItemsSettings itemsConfig) {
        Item[] items = new Item[ITEMS_COUNT];
        Random random = new Random();

        for (int i = 0; i < items.length; i++) {
            double price = random.nextInt(itemsConfig.getMaxItemPrice() * 100 + 1) / 100d;
            double weight = random.nextInt(itemsConfig.getMaxItemWeight() * 100 + 1) / 100d;
            boolean isTaken = random.nextBoolean();
            items[i] = new Item(price, weight, isTaken);
        }

        return items;
    }
}
