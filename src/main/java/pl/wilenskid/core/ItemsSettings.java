package pl.wilenskid.core;

public class ItemsSettings {
    private final int maxItemPrice;
    private final int maxItemWeight;

    public ItemsSettings(int maxItemPrice, int maxItemWeight) {
        this.maxItemPrice = maxItemPrice;
        this.maxItemWeight = maxItemWeight;
    }

    public int getMaxItemPrice() {
        return maxItemPrice;
    }

    public int getMaxItemWeight() {
        return maxItemWeight;
    }
}
