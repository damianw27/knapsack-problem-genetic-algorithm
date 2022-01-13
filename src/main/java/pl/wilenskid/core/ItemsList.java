package pl.wilenskid.core;

import java.util.List;
import java.util.Random;

public class ItemsList {
    private final Random random;
    private final List<Item> items;

    public ItemsList(List<Item> items) {
        this.random = new Random();
        this.items = items;
    }
}
