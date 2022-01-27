package pl.wilenskid.core;

import lombok.Getter;

@Getter
public class ItemBean {

    private final double weight;
    private final double price;

    public ItemBean(Item item) {
        this.weight = item.getWeight();
        this.price = item.getPrice();
    }
}
