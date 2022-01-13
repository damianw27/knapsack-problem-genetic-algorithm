package pl.wilenskid.alghoritm.selection;

import pl.wilenskid.core.Knapsack;

import java.util.List;
import java.util.Random;

public abstract class SelectionMethod {

    public static double TEST_GROUP_SIZE_FACTOR = 1.0 / 3.0;

    protected final Random rnd;

    protected SelectionMethod() {
        this.rnd = new Random();
    }

    public abstract List<Knapsack> select(List<Knapsack> knapsacks, boolean isMax);

}
