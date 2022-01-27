package pl.wilenskid.core;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class KnapsackBean {
    private final List<ItemBean> items;
    private final String configuration;
    private final double capacity;

    public KnapsackBean(Knapsack knapsack) {
        this.items = Arrays.stream(knapsack.getItems()).map(ItemBean::new).collect(Collectors.toList());
        this.configuration = knapsack.getBinaryString();
        this.capacity = knapsack.getCapacity();
    }
}
