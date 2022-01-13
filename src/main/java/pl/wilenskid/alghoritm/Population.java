package pl.wilenskid.alghoritm;

import lombok.Getter;
import pl.wilenskid.core.Item;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Population {

    private final Item[] items;
    private final List<Knapsack> firstPopulation;
    private final Map<Integer, List<Knapsack>> generations;
    private final int m;

    public Population(Item[] items, double knapsackCapacity, int count, int m) {
        this.items = items;
        this.firstPopulation = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            this.firstPopulation.add(new Knapsack(items, knapsackCapacity));
        }

        this.generations = new HashMap<>();
        this.generations.put(0, firstPopulation);
        this.m = m;
    }

}
