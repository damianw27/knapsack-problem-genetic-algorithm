package pl.wilenskid.alghoritm.crossing;

import pl.wilenskid.core.Knapsack;

import java.util.List;

public interface CrossingMethod {
    List<Knapsack> cross(List<Knapsack> knapsacks, double probability);
}
