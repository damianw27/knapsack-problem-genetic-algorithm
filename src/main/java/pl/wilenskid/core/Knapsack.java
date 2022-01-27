package pl.wilenskid.core;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Knapsack implements Comparable<Knapsack> {
    public static final Random RANDOM = new Random();

    private final Item[] items;
    private final KnapsackPair knapsackPair;
    private final double capacity;

    public Knapsack(Item[] items, double capacity, KnapsackPair parents) {
        this.items = new Item[items.length];

        for (int i = 0; i < items.length; i++) {
            Item currentItem = items[i];
            this.items[i] = new Item(currentItem.getWeight(), currentItem.getPrice(), new Random().nextBoolean());
        }

        this.knapsackPair = parents;
        this.capacity = capacity;
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

    public Knapsack(KnapsackBean knapsackBean) {
        List<ItemBean> itemBeans = knapsackBean.getItems();
        Item[] items = new Item[itemBeans.size()];

        for (int i = 0; i < itemBeans.size(); i++) {
            char isTakenChar = knapsackBean.getConfiguration().charAt(i);
            items[i] = new Item(itemBeans.get(i), isTakenChar == '1');
        }

        this.items = items;
        this.knapsackPair = null;
        this.capacity = knapsackBean.getCapacity();
    }

    @Override
    public int compareTo(Knapsack knapsack) {
        return Double.compare(this.calculateAdaptationValue(), knapsack.calculateAdaptationValue());
    }

    public boolean isOverflowing() {
        return calculateItemsWeight() > capacity;
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

    public double calculateItemsWeight() {
        double itemsWeight = 0;

        for (Item item : items) {
            if (!item.isTaken()) {
                continue;
            }

            itemsWeight += item.getWeight();
        }

        return itemsWeight;
    }

    public double calculateItemsPrice() {
        double itemsPrice = 0;

        for (Item item : items) {
            if (!item.isTaken()) {
                continue;
            }

            itemsPrice += item.getPrice();
        }

        return itemsPrice;
    }

}
