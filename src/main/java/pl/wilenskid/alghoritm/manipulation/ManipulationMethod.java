package pl.wilenskid.alghoritm.manipulation;

import pl.wilenskid.core.Knapsack;

import java.util.List;

public interface ManipulationMethod {
    List<Knapsack> manipulate(List<Knapsack> knapsacks, double probability);
}
