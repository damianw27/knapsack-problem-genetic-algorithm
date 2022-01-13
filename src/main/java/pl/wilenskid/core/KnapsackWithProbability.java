package pl.wilenskid.core;

import lombok.Value;
import pl.wilenskid.core.Knapsack;

@Value
public class KnapsackWithProbability {
    Knapsack subject;
    double probability;
    double distribution;
}
