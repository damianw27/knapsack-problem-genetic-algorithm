package pl.wilenskid.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item implements Comparable<Item> {

    private final double weight;
    private final double price;
    private final double value;

    private boolean isTaken;

    public Item(double weight, double price, boolean isTaken) {
        this.weight = weight;
        this.price = price;
        this.value = price / weight;
        this.isTaken = isTaken;
    }

    public Item(ItemBean itemBean, boolean isTaken) {
        this.weight = itemBean.getWeight();
        this.price = itemBean.getPrice();
        this.value = price / weight;
        this.isTaken = isTaken;
    }

    @Override
    public int compareTo(Item item) {
        return Double.compare(item.value, value);
    }

}
