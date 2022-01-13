package pl.wilenskid.core;

import lombok.Getter;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Knapsack implements Comparable<Knapsack> {
    private final Item[] items;
    private final KnapsackPair knapsackPair;
    private final double capacity;

    public Knapsack(Item[] items, double capacity, KnapsackPair parents) {
        this.items = items;
        this.knapsackPair = parents;
        this.capacity = capacity;

        for (Item item : items) {
            item.setTaken(new Random().nextBoolean());
        }
    }

    public Knapsack(Knapsack knapsack, String binaryString, KnapsackPair knapsackPair) {
        this(knapsack.getItems(), knapsack.getCapacity(), knapsackPair);

        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            char state = binaryString.charAt(i);
            item.setTaken(state == '1');
        }
    }

    public Knapsack(Item[] items, double capacity) {
        this(items, capacity, null);
    }

    public Knapsack(Knapsack knapsack) {
        this(knapsack.getItems(), knapsack.getCapacity(), knapsack.getKnapsackPair());
    }

    @Override
    public int compareTo(Knapsack knapsack) {
        return Double.compare(this.calculateAdaptationValue(), knapsack.calculateAdaptationValue());
    }

    public boolean isOverflowing() {
        return getItemsWeight() > capacity;
    }

    public void setRandomItemNotTaken() {
        int randomItemIndex = new Random().nextInt(items.length);
        items[randomItemIndex].setTaken(false);
    }

    public String getBinaryString() {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(items)
                .sequential()
                .map(item -> item.isTaken() ? '1' : '0')
                .collect(Collectors.toList())
                .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }

    public double calculateAdaptationValue() {
        double adaptationValue = 0;

        for (Item item : items) {
            if (!item.isTaken()) {
                continue;
            }

            adaptationValue += item.getValue();
        }

        return adaptationValue;
    }

    private double getItemsWeight() {
        double itemsWeight = 0;

        for (Item item : items) {
            if (!item.isTaken()) {
                continue;
            }

            itemsWeight += item.getWeight();
        }

        return itemsWeight;
    }

}
