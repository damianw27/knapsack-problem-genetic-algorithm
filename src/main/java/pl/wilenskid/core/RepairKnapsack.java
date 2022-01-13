package pl.wilenskid.core;

public class RepairKnapsack {
    public static Knapsack repair(Knapsack knapsack) {
        while (knapsack.isOverflowing()) {
            knapsack.setRandomItemNotTaken();
        }

        return knapsack;
    }
}
